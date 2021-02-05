package com.putoet.day23;

public class AddressedPacket extends Packet {
    public final long address;

    public AddressedPacket(long address, long x, long y) {
        super(x, y);
        this.address = address;
    }

    public int address() {
        return (int) address;
    }

    @Override
    public String toString() {
        return String.format("ADDR=%d,X=%d,Y=%d", address, x, y);
    }
}
