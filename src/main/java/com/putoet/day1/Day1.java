package com.putoet.day1;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        final var puzzleinput = ResourceLines.list("/day1.txt");

        Timer.run(() -> part1(puzzleinput));
        Timer.run(() -> part2(puzzleinput));
    }

    static void part1(List<String> puzzleinput) {
        final var partOne = puzzleinput.stream()
                .map(Mass::new)
                .map(Mass::requiredFuelForMass)
                .reduce(new Fuel(0), Fuel::add);
        System.out.println("sum of the fuel requirements " + partOne.fuel());
    }

    static void part2(List<String> puzzleinput) {
        final var partTwo = puzzleinput.stream()
                .map(Mass::new)
                .map(Mass::requiredFuel)
                .reduce(new Fuel(0), Fuel::add);
        System.out.println("sum of the fuel requirements for all of the modules on your spacecraft when also taking into account the mass of the added fuel " + partTwo.fuel());
    }
}


