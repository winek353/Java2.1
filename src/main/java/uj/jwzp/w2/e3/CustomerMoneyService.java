package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;
import java.util.HashMap;

public final class CustomerMoneyService {

    private final HashMap<Customer, BigDecimal> customersMoney = new HashMap<>();
    private final PersistenceLayer persistenceLayer;

    public CustomerMoneyService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    public BigDecimal getMoney(Customer customer) {
        if (customersMoney.containsKey(customer)) {
            return customersMoney.get(customer);
        } else {
            customersMoney.put(customer, BigDecimal.TEN);
            persistenceLayer.saveCustomer(customer);
            return customersMoney.get(customer);
        }
    }

    public boolean pay(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        if (money.compareTo(amount) >= 0) {
            customersMoney.put(customer, money.subtract(amount));
            persistenceLayer.saveCustomer(customer);
            return true;
        }
        return false;
    }

    public void addMoney(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        persistenceLayer.saveCustomer(customer);
        customersMoney.put(customer, money.add(amount));
    }

}
