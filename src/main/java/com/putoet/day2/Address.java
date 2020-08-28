package com.putoet.day2;

public class Address {
    static final Address START_ADDRESS = Address.of(0);

    private final int address;

    private Address(int address) {
        assert address >= 0;
        this.address = address;
    }

    static Address of(int address) {
        return new Address(address);
    }

    Address increase(int instructionSize) {
        return new Address(address + instructionSize);
    }

    int toInt() {
        return address;
    }
}
