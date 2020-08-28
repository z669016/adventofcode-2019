package com.putoet.day14;

import java.util.Optional;

public class Day14 {
    public static void main(String[] args) {
        final FuelReactions fuelReactions = new FuelReactions(FuelReactions.loadFile("/day14.txt"));
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionFor(Chemical.FUEL);

        if (reaction.isEmpty()) {
            System.out.println("No chemical reaction for found for FUEL");
        } else {
            final Optional<ChemicalReaction> newReaction = fuelReactions.simplifyChemicalReaction(reaction.get());
            if (newReaction.isEmpty()) {
                System.out.println("No new reaction possible for " + reaction.get().result());
            }
            System.out.println("Replacement for " + reaction.get().result() + " is "  + newReaction);
        }

        final Optional<ChemicalReaction> maxFuelTReaction = fuelReactions.maxFuelReactionFor(1_000_000_000_000L);
        if (maxFuelTReaction.isEmpty()) {
            System.out.println("No maximum fuel reaction possible.");
        } else {
            System.out.println("Maximum fuel reaction is: " + maxFuelTReaction.get());
        }
    }
}
