package com.putoet.day9;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        final var intCode = CSV.list("/day9.txt", Long::parseLong).get(0);
        Timer.run(() -> part(intCode, "part 1", 1L));
        Timer.run(() -> part(intCode, "part 2", 2L));
    }

    private static void part(List<Long> intCode, String part, long inputValue) {
        final var memory = new ExpandableMemory(intCode);
        final var input = new IntCodeInputOutputDevice();
        final var output = new IntCodeInputOutputDevice();
        final var device = IntCodeComputer.builder()
                .memory(memory)
                .input(input)
                .output(output)
                .build();

        input.offer(inputValue);
        device.run();

        System.out.println("Available output for " + part + " is " + output.asList());
    }
}