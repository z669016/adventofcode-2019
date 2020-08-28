package com.putoet.day1;

public class Fuel {
    private final long fuel;

    protected Fuel(long fuel) {
        this.fuel = fuel;
    }

    public Fuel add(Fuel other) {
        return new Fuel(fuel + other.fuel);
    }

    public boolean isEmpty() {
        return fuel == 0;
    }

    public Mass asMass() {
        return new Mass(toString());
    }

    @Override
    public String toString() {
        return String.valueOf(fuel);
    }
}
