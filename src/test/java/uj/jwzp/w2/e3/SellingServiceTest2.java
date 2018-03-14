package uj.jwzp.w2.e3;

import mockit.MockUp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingServiceTest2 {

    @Mock
    private PersistenceLayer persistenceLayer;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private SellingService uut;

    @Before
    public void set(){
        uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
    }

    public void setDiscountsConfigMock(Boolean isWeekendPromotion, BigDecimal DiscountForItem){
        new MockUp<DiscountsConfig>(){
            @mockit.Mock
            public boolean isWeekendPromotion() {
                return isWeekendPromotion;
            }
            @mockit.Mock
            public BigDecimal getDiscountForItem(Item item, Customer customer) {
                return DiscountForItem;
            }
        };
    }

    @Test
    public void notSell() {
        //given
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        setDiscountsConfigMock(Boolean.FALSE,  BigDecimal.ZERO);

        //when
        boolean sold = uut.sell(i, 7, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.moneyService.getMoney(c));
    }

    @Test
    public void sell() {
        //given
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        setDiscountsConfigMock(Boolean.FALSE,  BigDecimal.ZERO);

        //when
        boolean sold = uut.sell(i, 1, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellALotWithoutWeekendPromotion() {
        //given
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));

        setDiscountsConfigMock(Boolean.FALSE,  BigDecimal.ZERO);

        //when
        boolean sold = uut.sell(i, 10, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(970),  uut.moneyService.getMoney(c));
    }
    @Test
    public void sellALotWithWeekendPromotion() {
        //given
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));

        setDiscountsConfigMock(Boolean.TRUE, BigDecimal.ZERO);

        //when
        boolean sold = uut.sell(i, 10, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(973),  uut.moneyService.getMoney(c));
    }

    @Test
    public void sellWithDiscountForItem (){
        //given
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        setDiscountsConfigMock(Boolean.FALSE,  new BigDecimal(1));

        //when
        boolean sold = uut.sell(i, 1, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(8),  uut.moneyService.getMoney(c));
    }
}


