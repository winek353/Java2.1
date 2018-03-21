package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;
import java.util.HashMap;

public class CustomerMoneyService {

    private final HashMap<Customer, BigDecimal> customerToMoney = new HashMap<>();
    private final PersistenceLayer persistenceLayer;

    public CustomerMoneyService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    public void addCustomer(Customer customer){
        customerToMoney.put(customer, BigDecimal.TEN);
        persistenceLayer.saveCustomer(customer);
    }

    public BigDecimal getMoney(Customer customer) {//można przerobić dodawanie nowego klienta
        if (customerToMoney.containsKey(customer)) {
            return customerToMoney.get(customer);
        } else {
            addCustomer(customer);
            return customerToMoney.get(customer);
        }
    }

    public boolean canAffordToPay(Customer customer, BigDecimal amount){
        BigDecimal customerMoney = getMoney(customer);
        if (customerMoney.compareTo(amount) >= 0)
            return true;
        else
            return false;
    }

    public void substractMoney(Customer customer, BigDecimal amount) {
        BigDecimal customerMoney = getMoney(customer);
        customerToMoney.put(customer, customerMoney.subtract(amount));
        persistenceLayer.saveCustomer(customer);
    }

    public void addMoney(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        persistenceLayer.saveCustomer(customer);
        customerToMoney.put(customer, money.add(amount));
    }
}
