package com.putoet.day2;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day2.txt", Long::parseLong);

        Timer.run(() -> part1(intCode));
        Timer.run(() -> part2(intCode));
    }

    private static void part1(List<Long> intCode) {
        final var memory = new FixedMemory(intCode);
        final var device = IntCodeComputer.builder().memory(memory).build();

        memory.poke(new Address(1), 12);
        memory.poke(new Address(2), 2);

        device.run();
        System.out.println("The value at position 0 after running the program is " + memory.peek(new Address(0)));
    }

    private static void part2(List<Long> intCode) {
        final var nounAddress = new Address(1);
        final var verbAddress = new Address(2);

        for (var noun = 0; noun < intCode.size(); noun++) {
            for (var verb = 0; verb < intCode.size(); verb++) {
                final var memory = new FixedMemory(intCode);
                final var device = IntCodeComputer.builder().memory(memory).build();

                memory.poke(nounAddress, noun);
                memory.poke(verbAddress, verb);

                device.run();

                final var value = memory.peek(Address.START);
                if (value == 19_690_720L) {
                    System.out.printf("noun=%d and verb=%d, 100*noun+verb=%d%n", noun, verb, (100 * noun + verb));
                    break;
                }
            }
        }
    }
}




