package com.putoet.day14;

import java.util.Optional;

public class Day14 {
    public static void main(String[] args) {
        final NanoFactory nanoFactory = new NanoFactory(NanoFactory.loadFile("/day14.txt"));

        pert1(nanoFactory);
        System.out.println();
        part2(nanoFactory);
    }

    private static void pert1(NanoFactory nanoFactory) {
        System.out.println("Minimal ORE for 1 fuel is " + nanoFactory.minimalOreForFuel());
    }

    private static void part2(NanoFactory nanoFactory) {
        final Optional<ChemicalReaction> maxFuelTReaction = nanoFactory.maxFuelReactionFor(1_000_000_000_000L);
        if (maxFuelTReaction.isEmpty()) {
            System.out.println("No maximum fuel reaction possible.");
        } else {
            System.out.println("Maximum fuel reaction is: " + maxFuelTReaction.get());
        }
    }
}
