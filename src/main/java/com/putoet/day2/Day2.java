package com.putoet.day2;

import com.putoet.resources.CSV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) throws IOException {
        final List<Integer> intCode = CSV.flatList("/day2.txt", Integer::parseInt);

        // Part one
        Memory memory = Memory.of(intCode);
        memory.poke(Address.of(1), 12);
        memory.poke(Address.of(2), 2);

        Processor processor = new Processor(memory);
        processor.run();
        System.out.println("The valeua at position 0 after running the program is " + memory.peek(Address.of(0)));

        // Brute force solution on part two
        final Address nounAddress = Address.of(1);
        final Address verbAddress = Address.of(2);
        for (int noun = 0; noun < intCode.size(); noun++) {
            for (int verb = 0; verb < intCode.size(); verb++) {
                memory = Memory.of(intCode);
                memory.poke(nounAddress, noun);
                memory.poke(verbAddress, verb);

                processor = new Processor(memory);
                processor.run();

                int value = processor.memory().peek(Address.START_ADDRESS);
                if (value == 19690720) {
                    System.out.println("noun=" + noun + " and verb=" + verb);
                    System.out.println("100 * noun + verb = " + (100 * noun + verb));
                    break;
                }
            }
        }
    }
}




