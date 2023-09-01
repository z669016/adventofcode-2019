package com.putoet.day5;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        final var intCode = CSV.list("/day5.txt", Long::parseLong).get(0);

        Timer.run(() -> part1(intCode));
        Timer.run(() -> part2(intCode));
    }

    private static void part1(List<Long> intCode) {
        final var memory = new FixedMemory(intCode);
        final var input = new IntCodeInputOutputDevice();
        final var output = new IntCodeInputOutputDevice();
        final var device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(1L);
        device.run();
        System.out.println("The value from the output for part 1 is " + output.asList());

    }

    private static void part2(List<Long> intCode) {
        final var memory = new FixedMemory(intCode);
        final var input = new IntCodeInputOutputDevice();
        final var output = new IntCodeInputOutputDevice();
        final var device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(5L);
        device.run();
        System.out.println("The value from the output for part 2 is " + output.asList());
    }
}




