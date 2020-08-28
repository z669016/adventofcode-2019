package com.putoet.day4;

import java.util.Iterator;
import java.util.function.Consumer;

public class Day4 {

    public static final int MIN = 152085;
    public static final int MAX = 670283;

    public static void main(String[] args) {
        final GeneratorA generatorA = new GeneratorA(MIN, MAX);
        final GeneratorB generatorB = new GeneratorB(MIN, MAX);

        final Counter counterA = new Counter();
        generatorA.forEachRemaining(counterA);
        System.out.println("Counter for puzzle A is " + counterA.counter());

        final Counter counterB = new Counter();
        generatorB.forEachRemaining(counterB);
        System.out.println("Counter for puzzle B is " + counterB.counter());
    }
}

