package com.putoet.intcode;

import java.util.Objects;

public class Address {
    private final int address;

    public static final Address START = new Address(0);

    static class IllegalAddress extends IllegalArgumentException {
        public IllegalAddress(long address) {
            super("IntAddress cannot be negative " + address);
        }
    }

    public Address(int address) {
        if (address < 0) throw new Address.IllegalAddress(address);
        this.address = address;
    }

    public Address(long address) {
        if (address < 0 || address > Integer.MAX_VALUE) throw new Address.IllegalAddress(address);
        this.address = (int) address;
    }

    public Address increase(int offset) {
        return new Address(address + offset);
    }

    public Integer intValue() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address that = (Address) o;
        return address == that.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return String.valueOf(address);
    }
}

