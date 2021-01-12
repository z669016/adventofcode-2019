package com.putoet.day9;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;

import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.list("/day9.txt", Long::parseLong).get(0);
        part(intCode, "part 1", 1L);
        part(intCode, "part 1", 2L);
    }

    private static void part(List<Long> intCode, String part, long inputValue) {
        final Memory memory = new ExpandableMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder()
                .memory(memory)
                .input(input)
                .output(output)
                .build();

        input.offer(inputValue);
        device.run();

        System.out.println("Available output for " + part + " is " + output.asList());
    }
}