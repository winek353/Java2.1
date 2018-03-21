package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingServiceTest {

    @Mock
    private PersistenceLayer persistenceLayer;

    @Mock
    DiscountsConfigProxy discountsConfigProxy;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    SellingService uut;

    CustomerMoneyService customerMoneyService;

    Item i;

    Customer c;

    @Before
    public void set(){
        customerMoneyService = new CustomerMoneyService(persistenceLayer);
        uut = new SellingService(persistenceLayer, customerMoneyService, BigDecimal.valueOf(3),
                BigDecimal.valueOf(5), discountsConfigProxy);

        i = new Item("i", new BigDecimal(3));
        c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);

//        Mockito.when(persistenceLayer.saveTransaction(c, i, quantity ))
//                .thenReturn(Boolean.TRUE);
    }

    @Test
    public void notEnoughMoney() {
        //given
        int quantity = 7;

        Mockito.when(discountsConfigProxy.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountsConfigProxy.getDiscountForItem(i,c)).thenReturn(BigDecimal.ZERO);

        Mockito.when(persistenceLayer.saveTransaction(c, i, quantity ))
                .thenReturn(Boolean.TRUE);
        //when
        boolean isSold = uut.sell(i, quantity, c);

        //then
        Assert.assertFalse(isSold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.getCustomerMoneyService().getMoney(c));
    }

    @Test
    public void sell() {
        //given
        int quantity = 1;

        Mockito.when(discountsConfigProxy.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsConfigProxy.getDiscountForItem(i,c)).thenReturn(BigDecimal.ZERO);

        Mockito.when(persistenceLayer.saveTransaction(c, i, quantity ))
                .thenReturn(Boolean.TRUE);

        //when
        boolean isSold = uut.sell(i, quantity, c);

        //then
        Assert.assertTrue(isSold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.getCustomerMoneyService().getMoney(c));
    }

    @Test
    public void sellWithDiscountForItem() {
        //given
        int quantity = 2;

        Mockito.when(discountsConfigProxy.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountsConfigProxy.getDiscountForItem(i,c)).thenReturn(BigDecimal.valueOf(1));

        Mockito.when(persistenceLayer.saveTransaction(c, i, quantity ))
                .thenReturn(Boolean.TRUE);

        //when
        boolean isSold = uut.sell(i, quantity, c);

        //then
        Assert.assertTrue(isSold);
        Assert.assertEquals(BigDecimal.valueOf(6), uut.getCustomerMoneyService().getMoney(c));
    }

    @Test
    public void sellALotWithWeekendPromotion() {
        //given
        int quantity = 11;

        Mockito.when(discountsConfigProxy.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsConfigProxy.getDiscountForItem(i,c)).thenReturn(BigDecimal.ZERO);

        Mockito.when(persistenceLayer.saveTransaction(c, i, quantity ))
                .thenReturn(Boolean.TRUE);

        uut.getCustomerMoneyService().addMoney(c, new BigDecimal(990));
        //when
        boolean isSold = uut.sell(i, quantity, c);

        //then
        Assert.assertTrue(isSold);
        Assert.assertEquals(BigDecimal.valueOf(970), uut.getCustomerMoneyService().getMoney(c));
    }

//    @Test
//    public void sell() {
//        //given
//        Mockito.when(customerMoneyService.canAffordToPay( eq(c), Mockito.any()) ).thenReturn(Boolean.TRUE);
//
//        Mockito.when(discountsConfigProxy.isWeekendPromotion()).thenReturn(Boolean.FALSE);
//        Mockito.when(discountsConfigProxy.getDiscountForItem(i,c)).thenReturn(BigDecimal.ZERO);
//
//        //when
//        boolean isSold = uut.sell(i, 1, c);
//
//        //then
//        Assert.assertFalse(isSold);
//    }
//
//    @Test
//    public void sellWithWeekendPromotion() {
//        //given
//        Mockito.when(customerMoneyService.canAffordToPay( eq(c), lt(BigDecimal.valueOf(10))) ).thenReturn(Boolean.TRUE);
//
//        Mockito.when(discountsConfigProxy.isWeekendPromotion()).thenReturn(Boolean.TRUE);
//        Mockito.when(discountsConfigProxy.getDiscountForItem(i,c)).thenReturn(BigDecimal.ZERO);
//
//        //when
//        boolean isSold = uut.sell(i, 2, c);
//
//        //then
//        Assert.assertFalse(isSold);
//    }
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    @Test
//    public void sell() {
//        //given
//        SellingService uut = new SellingService(persistenceLayer);
//        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
//        Item i = new Item("i", new BigDecimal(3));
//        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
//
//        //when
//        boolean sold = uut.sell(i, 1, c);
//
//        //then
//        Assert.assertFalse(sold);
//        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
//    }

//    @Test
//    @PrepareForTest(DiscountsConfig.class)
//    public void sellALot() {
//        //given
//        SellingService uut = new SellingService(persistenceLayer);
//        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
//        Item i = new Item("i", new BigDecimal(3));
//        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
//        uut.moneyService.addMoney(c, new BigDecimal(990));
//        PowerMockito.mockStatic(DiscountsConfig.class);
//        PowerMockito.when(DiscountsConfig.isWeekendPromotion()).thenReturn(Boolean.FALSE);
//        PowerMockito.when(DiscountsConfig.getDiscountForItem(i, c)).thenReturn(BigDecimal.ZERO);
//
//        //when
//        boolean sold = uut.sell(i, 10, c);
//
//        //then
//        Assert.assertFalse(sold);
//        Assert.assertEquals(BigDecimal.valueOf(970),  uut.moneyService.getMoney(c));
//    }
}


