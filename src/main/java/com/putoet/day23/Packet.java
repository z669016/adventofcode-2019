package com.putoet.day23;

class Packet {
    public final long x;
    public final long y;

    public Packet(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("X=%d,Y=%d", x, y);
    }
}
