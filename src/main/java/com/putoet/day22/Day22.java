package com.putoet.day22;

import com.putoet.resources.ResourceLines;

import java.math.BigInteger;
import java.util.List;

/**
 * Solution to Advent of Code 2019 Day 22
 * The original puzzle can be found here: https://adventofcode.com/2019/day/22
 */
public final class Day22 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day22.txt");

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        final DeckShuffle deckShuffle = new DeckShuffle(lines, BigInteger.valueOf(10_007), BigInteger.ONE);
        System.out.println("The position of card 2019 is " + deckShuffle.positionOf(BigInteger.valueOf(2019)));
    }

    private static void part2(List<String> lines) {
        final DeckShuffle deckShuffle = new DeckShuffle(lines, BigInteger.valueOf(119_315_717_514_047L), BigInteger.valueOf(101_741_582_076_661L));
        System.out.println("The position of card 2019 is " + deckShuffle.cardAt(BigInteger.valueOf(2020)));
    }
}