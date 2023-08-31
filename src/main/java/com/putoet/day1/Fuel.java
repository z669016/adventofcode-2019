package com.putoet.day1;

record Fuel(long fuel) {
    public Fuel add(Fuel other) {
        return new Fuel(fuel + other.fuel);
    }
    public boolean isEmpty() {
        return fuel == 0;
    }
    public Mass asMass() {
        return new Mass(fuel);
    }
}
