package com.putoet.day2;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;

import java.io.IOException;
import java.util.List;

public class Day2 {
    public static void main(String[] args) throws IOException {
        final List<Integer> intCode = CSV.flatList("/day2.txt", Integer::parseInt);

        part1(intCode);
        part2(intCode);
    }

    private static void part1(List<Integer> intCode) {
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = new IntCodeComputer(memory);

        memory.poke(new Address(1), 12);
        memory.poke(new Address(2), 2);

        device.run();
        System.out.println("The value at position 0 after running the program is " + memory.peek(new Address(0)));
    }

    private static void part2(List<Integer> intCode) {
        final Address nounAddress = new Address(1);
        final Address verbAddress = new Address(2);

        for (int noun = 0; noun < intCode.size(); noun++) {
            for (int verb = 0; verb < intCode.size(); verb++) {
                final Memory memory = new FixedMemory(intCode);
                final IntCodeDevice device = new IntCodeComputer(memory);

                memory.poke(nounAddress, noun);
                memory.poke(verbAddress, verb);

                device.run();

                long value = memory.peek(Address.START);
                if (value == 19_690_720L) {
                    System.out.println("noun=" + noun + " and verb=" + verb);
                    System.out.println("100 * noun + verb = " + (100 * noun + verb));
                    break;
                }
            }
        }
    }
}




