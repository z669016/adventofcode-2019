package com.putoet.day9;

public class Registers {
    private final Address ip;
    private final Address rb;

    public Registers() {
        ip = Address.START_ADDRESS;
        rb = Address.START_ADDRESS;
    }

    public Registers(Address ip, Address rb) {
        if (ip == null)
            throw new IllegalArgumentException("Instruction pointer cannot be null");
        if (rb == null)
            throw new IllegalArgumentException("Relative base cannot be null");

        this.ip = ip;
        this.rb = rb;
    }

    public Address ip() {
        return ip;
    }

    public Address rb() {
        return rb;
    }

    public Registers withIncreasedInstructionPointer(int offset) {
        return new Registers(ip.increase(offset), rb);
    }

    public Registers withIncreasedRelativeBase(int offset) {
        return new Registers(ip, rb.increase(offset));
    }

    @Override
    public String toString() {
        return String.format("IP=%s, RB=%s", ip(), rb());
    }
}
