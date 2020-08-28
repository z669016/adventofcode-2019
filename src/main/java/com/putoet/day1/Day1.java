package com.putoet.day1;

import com.putoet.resources.ResourceLines;

public class Day1 {
    public static void main(String[] args) {
        final Fuel partOne = ResourceLines.stream("/day1.txt")
                .map(Mass::new)
                .map(Mass::requiredFuelForMass)
                .reduce(new Fuel(0), Fuel::add);
        System.out.println("sum of the fuel requirements " + partOne);

        final Fuel partTwo = ResourceLines.stream("/day1.txt")
                .map(Mass::new)
                .map(Mass::requiredFuel)
                .reduce(new Fuel(0), Fuel::add);
        System.out.println("sum of the fuel requirements for all of the modules on your spacecraft when also taking into account the mass of the added fuel " + partTwo);
    }
}


