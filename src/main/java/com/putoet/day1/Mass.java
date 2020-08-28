package com.putoet.day1;

public class Mass {
    private final Long mass;

    public Mass(String mass) {
        this.mass = Long.valueOf(mass);
    }

    public Fuel requiredFuel() {
        Fuel total = new Fuel(0);
        Fuel additionalfuel = requiredFuelForMass(this);
        while (!additionalfuel.isEmpty()) {
            total = total.add(additionalfuel);
            additionalfuel = requiredFuelForMass(additionalfuel.asMass());
        }
        return total;
    }

    public static Fuel requiredFuelForMass(Mass mass) {
        long fuel =  (mass.mass / 3) - 2;
        return new Fuel(fuel >= 0 ? fuel : 0);
    }
}
