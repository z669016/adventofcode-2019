package com.putoet.day9;

import com.putoet.intcode.ExpandableMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.Memory;
import com.putoet.resources.CSV;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Day9 {
    public static void main(String[] args) {
        final List<Integer> intCode = CSV.list("/day9.txt", Integer::parseInt).get(0);
        part(intCode, "part 1", 1L);
        part(intCode, "part 1", 2L);
    }

    private static void part(List<Integer> intCode, String part, long inputValue) {
        final Memory memory = new ExpandableMemory(intCode);
        final BlockingDeque<Long> input = new LinkedBlockingDeque<>();
        final Queue<Long> output = new LinkedList<>();
        final IntCodeDevice device = IntCodeComputer.builder()
                .memory(memory)
                .input(input)
                .output(output)
                .build();

        input.offer(inputValue);
        device.run();

        System.out.println("Available output for " + part + " is " + output);
    }
}