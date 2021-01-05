package com.putoet.day1;

import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        final List<String> puzzleinput = ResourceLines.list("/day1.txt");

        part1(puzzleinput);
        part2(puzzleinput);
    }

    private static void part1(List<String> puzzleinput) {
        final Fuel partOne = puzzleinput.stream()
                .map(Mass::new)
                .map(Mass::requiredFuelForMass)
                .reduce(new Fuel(0), Fuel::add);
        System.out.println("sum of the fuel requirements " + partOne);
    }

    private static void part2(List<String> puzzleinput) {
        final Fuel partTwo = puzzleinput.stream()
                .map(Mass::new)
                .map(Mass::requiredFuel)
                .reduce(new Fuel(0), Fuel::add);
        System.out.println("sum of the fuel requirements for all of the modules on your spacecraft when also taking into account the mass of the added fuel " + partTwo);
    }
}


