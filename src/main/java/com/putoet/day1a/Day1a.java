package com.putoet.day1a;

import com.putoet.resources.ResourceLines;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Mass {
    private final Long mass;

    public Mass(String mass) {
        this.mass = Long.valueOf(mass);
    }

    public Fuel requiredFuel() {
        long fuel =  (mass / 3) - 2;
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

    @Override
    public String toString() {
        return String.valueOf(fuel);
    }
}

public class Day1a {
    public static void main(String[] args) {
        System.out.println(ResourceLines.stream("/day1.txt")
                .map(Mass::new)
                .map(Mass::requiredFuel)
                .reduce(new Fuel(0), Fuel::add));
    }
}


