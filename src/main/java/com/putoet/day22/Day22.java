package com.putoet.day22;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.math.BigInteger;
import java.util.List;

public final class Day22 {
    public static void main(String[] args) {
        final var lines = ResourceLines.list("/day22.txt");

        Timer.run(() -> part1(lines));
        Timer.run(() -> part2(lines));
    }

    private static void part1(List<String> lines) {
        final var deckShuffle = new DeckShuffle(lines, BigInteger.valueOf(10_007), BigInteger.ONE);
        System.out.println("The position of card 2019 is " + deckShuffle.positionOf(BigInteger.valueOf(2019)));
    }

    private static void part2(List<String> lines) {
        final var deckShuffle = new DeckShuffle(lines, BigInteger.valueOf(119_315_717_514_047L), BigInteger.valueOf(101_741_582_076_661L));
        System.out.println("The position of card 2019 is " + deckShuffle.cardAt(BigInteger.valueOf(2020)));
    }
}