package com.putoet.day4;

public class Day4 {
    public static final int PUZZLE_INPUT_MIN = 152085;
    public static final int PUZZLE_INPUT_MAX = 670283;

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        final GeneratorA generatorA = new GeneratorA(PUZZLE_INPUT_MIN, PUZZLE_INPUT_MAX);
        final Counter counterA = new Counter();
        generatorA.forEachRemaining(counterA);
        System.out.println("Counter for puzzle A is " + counterA.counter());
    }

    private static void part2() {
        final GeneratorB generatorB = new GeneratorB(PUZZLE_INPUT_MIN, PUZZLE_INPUT_MAX);
        final Counter counterB = new Counter();
        generatorB.forEachRemaining(counterB);
        System.out.println("Counter for puzzle B is " + counterB.counter());
    }
}

