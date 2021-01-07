package com.putoet.day5;

import com.putoet.intcode.FixedMemory;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.Memory;
import com.putoet.resources.CSV;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day5 {
    public static void main(String[] args) {
        final List<Integer> intCode = CSV.list("/day5.txt", Integer::parseInt).get(0);

        part1(intCode);
        part2(intCode);
    }

    private static void part1(List<Integer> intCode) {
        final Memory memory = new FixedMemory(intCode);
        final Queue<Long> input = new LinkedList<>();
        final Queue<Long> output = new LinkedList<>();
        final IntCodeDevice device = new IntCodeComputer(memory, input, output);

        input.offer(1L);
        device.run();
        System.out.println("The value from the output for part 1 is " + output);

    }

    private static void part2(List<Integer> intCode) {
        final Memory memory = new FixedMemory(intCode);
        final Queue<Long> input = new LinkedList<>();
        final Queue<Long> output = new LinkedList<>();
        final IntCodeDevice device = new IntCodeComputer(memory, input, output);

        input.offer(5L);
        device.run();
        System.out.println("The value from the output for part 2 is " + output);

    }
}




