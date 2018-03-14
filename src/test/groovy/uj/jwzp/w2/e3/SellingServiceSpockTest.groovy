package uj.jwzp.w2.e3

import spock.lang.Specification
import uj.jwzp.w2.e3.external.PersistenceLayer

class SellingServiceSpockTest extends Specification{

    private PersistenceLayer persistenceLayer = Mock()
//    private SellingService sellingService = new SellingService(persistenceLayer)
//
//    def setup() {
//        GroovyMock(DiscountsConfig, global: true)
//    }

//    def "test1"(){
//        setup:
//        persistenceLayer.saveCustomer(_) >> Boolean.TRUE
//        Item i = new Item("i", new BigDecimal(3))
//        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza")
//
//        expect:
//        !sellingService.sell(i, 7, c)
//        sellingService.moneyService.getMoney(c) == BigDecimal.valueOf(10)
//    }
//    def "test2"(){
//        setup:
//        persistenceLayer.saveCustomer(_) >> Boolean.TRUE
//        Item i = new Item("i", new BigDecimal(3))
//        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza")
//
//        expect:
//        !sellingService.sell(i, 1, c)
//        sellingService.moneyService.getMoney(c) == BigDecimal.valueOf(7)
//    }
//    def "test3"(){
//        setup:
//        persistenceLayer.saveCustomer(_) >> Boolean.TRUE
//        Item i = new Item("i", new BigDecimal(3))
//        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza")
//        sellingService.moneyService.addMoney(c, new BigDecimal(990))
//        DiscountsConfig.isWeekendPromotion() >>  Boolean.FALSE
//        DiscountsConfig.getDiscountForItem(i,c) >> BigDecimal.ZERO
//
//        println( DiscountsConfig.isWeekendPromotion())
//        println ( DiscountsConfig.getDiscountForItem(i,c))
//        println(sellingService.moneyService.getMoney(c))
//
//        when:
//        boolean sold = sellingService.sell(i, 10, c)
//
//        then:
//        !sold
//        sellingService.moneyService.getMoney(c) == BigDecimal.valueOf(970)
//    }

//    def "test"(){
//        setup:
//        GroovyMock(DiscountsConfig, global: true)
//        DiscountsConfig.isWeekendPromotion() >> Boolean.FALSE
//        SellingService sellingService = new SellingService(persistenceLayer)
//
//        println DiscountsConfig.isWeekendPromotion()
//
//        when:
//        boolean b = sellingService.test()
//
//        then:
//        !b
//    }

//    def "anotherTest"(){
//        DiscountsConfig.metaClass.'static'.isWeekendPromotion = {
//
//        }
//    }


}
