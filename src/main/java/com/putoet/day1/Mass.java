package com.putoet.day1;

import org.jetbrains.annotations.NotNull;

record Mass(long mass) {
    public Mass(@NotNull String mass) {
        this(Long.parseLong(mass));
    }

    public Fuel requiredFuel() {
        var total = new Fuel(0);
        var additionalfuel = requiredFuelForMass(this);
        while (!additionalfuel.isEmpty()) {
            total = total.add(additionalfuel);
            additionalfuel = requiredFuelForMass(additionalfuel.asMass());
        }
        return total;
    }

    public static Fuel requiredFuelForMass(Mass mass) {
        long fuel = (mass.mass / 3) - 2;
        return new Fuel(fuel >= 0 ? fuel : 0);
    }
}
