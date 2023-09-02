package com.putoet.day21;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day21 {
    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day21.txt", Long::parseLong);
        Timer.run(() -> part1(intCode));
        Timer.run(() -> part2(intCode));
    }

    private static void part1(List<Long> intCode) {
        // J = D ^ NOT (A ^ B ^ C)
        final var device = new SpringDroidProgrammingDevice(intCode);
        final var output = device.springDroid(List.of(
                "NOT A T",
                "NOT T T",
                "AND B T",
                "AND C T",
                "NOT T J",
                "AND D J",
                "WALK"
        ));

        output.forEach(System.out::println);
    }

    private static void part2(List<Long> intCode) {
        // J = D ^ NOT (A ^ B ^ C) ^ (E v H)
        final var device = new SpringDroidProgrammingDevice(intCode);
        final var output = device.springDroid(List.of(
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

        output.forEach(System.out::println);
    }
}
