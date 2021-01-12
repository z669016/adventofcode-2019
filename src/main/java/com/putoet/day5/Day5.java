package com.putoet.day5;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Day5 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.list("/day5.txt", Long::parseLong).get(0);

        part1(intCode);
        part2(intCode);
    }

    private static void part1(List<Long> intCode) {
        final Memory memory = new FixedMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(1L);
        device.run();
        System.out.println("The value from the output for part 1 is " + output.asList());

    }

    private static void part2(List<Long> intCode) {
        final Memory memory = new FixedMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(5L);
        device.run();
        System.out.println("The value from the output for part 2 is " + output.asList());
    }
}




