package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public final class SellingService {

    private final PersistenceLayer persistenceLayer;
    private final CustomerMoneyService customerMoneyService;
    private final BigDecimal discountValue;
    private final BigDecimal discountThreshold;
    private final DiscountsConfigProxy discountsConfigProxy;

    public SellingService(PersistenceLayer persistenceLayer, CustomerMoneyService customerMoneyService,
                          BigDecimal discountValue, BigDecimal discountThreshold,
                          DiscountsConfigProxy discountsConfigProxy) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.customerMoneyService = customerMoneyService;
        this.discountValue = discountValue;
        this.discountThreshold = discountThreshold;
        this.discountsConfigProxy = discountsConfigProxy;
    }

    public BigDecimal calculatePrice (Item item, int quantity, Customer customer) {
        BigDecimal ItemPriceWithDiscounts = item.getPrice().subtract(discountsConfigProxy.getDiscountForItem(item, customer));
        BigDecimal totalPrice = ItemPriceWithDiscounts.multiply(BigDecimal.valueOf(quantity));
        if (discountsConfigProxy.isWeekendPromotion() && totalPrice.compareTo(discountThreshold) > 0)
           totalPrice = totalPrice.subtract(discountValue);
        return totalPrice;
    }

    public CustomerMoneyService getCustomerMoneyService() {
        return customerMoneyService;
    }

    public boolean sell(Item item, int quantity, Customer customer) {
        BigDecimal price = calculatePrice(item, quantity, customer);
        if (customerMoneyService.canAffordToPay(customer, price)) {
            customerMoneyService.substractMoney(customer, price);
            return persistenceLayer.saveTransaction(customer, item, quantity);
        }
        else
            return false;
    }
}
