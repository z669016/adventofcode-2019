package com.putoet.day21;

import com.putoet.resources.CSV;

import java.util.List;

public class Day21 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day21.txt", Long::parseLong);
        part1(intCode);
        part2(intCode);
    }

    private static void part1(List<Long> intCode) {
        // J = D ^ NOT (A ^ B ^ C)
        final SpringDroidProgrammingDevice device = new SpringDroidProgrammingDevice(intCode);
        final List<String> output = device.springDroid(List.of(
                "NOT A T",
                "NOT T T",
                "AND B T",
                "AND C T",
                "NOT T J",
                "AND D J",
                "WALK"
        ));

        System.out.println("Part 1");
        output.forEach(System.out::println);
    }

    private static void part2(List<Long> intCode) {
        // J = D ^ NOT (A ^ B ^ C) ^ (E v H)
        final SpringDroidProgrammingDevice device = new SpringDroidProgrammingDevice(intCode);
        final List<String> output = device.springDroid(List.of(
                "NOT A T",
                "NOT T T",
                "AND B T",
                "AND C T",
                "NOT T J",
                "AND D J",
                "NOT H T",
                "NOT T T",
                "OR E T",
                "AND T J",
                "RUN"
        ));

        System.out.println("Part 2");
        output.forEach(System.out::println);
    }
}
