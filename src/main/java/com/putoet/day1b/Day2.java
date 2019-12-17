package com.putoet.day1b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Mass {
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

    private static Fuel requiredFuelForMass(Mass mass) {
        long fuel =  (mass.mass / 3) - 2;
        return new Fuel(fuel >= 0 ? fuel : 0);
    }
}

class Fuel {
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

public class Day2 {
    public static void main(String[] args) throws IOException {
        System.out.println(Files.lines(Paths.get("modules.txt")).map(s -> new Mass(s)).map(m -> m.requiredFuel()).reduce(new Fuel(0), (f1, f2) -> f1.add(f2)));
    }
}


