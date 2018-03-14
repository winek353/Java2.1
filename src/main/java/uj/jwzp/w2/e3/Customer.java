package uj.jwzp.w2.e3;

public final class Customer {
    private final long id;
    private final String name;
    private final String address;

    public Customer(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
