package com.putoet.day9;

import java.util.Objects;

public class Address implements IAddress {
    public static final Address START_ADDRESS = Address.of(0);

    private final int address;

    private Address(int address) {
        if (address < 0) throw new IllegalArgumentException("Address cannot be negative " + address);
        this.address = address;
    }

    public static Address of(int address) {
        return new Address(address);
    }

    @Override
    public Address increase(int offset) {
        return new Address(address + offset);
    }

    @Override
    public int offset() {
        return address;
    }

    @Override
    public String toString() {
        return String.valueOf(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address1 = (Address) o;
        return address == address1.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
