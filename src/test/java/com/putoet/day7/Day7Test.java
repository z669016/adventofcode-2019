package com.putoet.day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day7Test {
    @BeforeEach
    public void setup() {
        AmplifierArray.enableLog();
    }

    @Test
    public void sample1Test() {
        final List<Integer> intCode = List.of(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0);
        final List<Integer> sequence = List.of(4, 3, 2, 1, 0);
        final AmplifierArray amplifierArray = AmplifierArray.builder()
                .intCode(intCode)
                .sequence(sequence)
                .inputSignal(0)
                .build();

        final Integer result = amplifierArray.run();
        assertEquals(43210, result.intValue());
    }

    @Test
    public void sample2Test() {
        final List<Integer> intCode = List.of(3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23,
                101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0);
        final List<Integer> sequence = List.of(0, 1, 2, 3, 4);
        final AmplifierArray amplifierArray = AmplifierArray.builder()
                .intCode(intCode)
                .sequence(sequence)
                .inputSignal(0)
                .build();

        final Integer result = amplifierArray.run();
        assertEquals(54321, result.intValue());
    }

    @Test
    public void sample3Test() {
        final List<Integer> intCode = List.of(3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33,
                1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0);
        final List<Integer> sequence = List.of(1, 0, 4, 3, 2);
        final AmplifierArray amplifierArray = AmplifierArray.builder()
                .intCode(intCode)
                .sequence(sequence)
                .inputSignal(0)
                .build();

        final Integer result = amplifierArray.run();
        assertEquals(65210, result.intValue());
    }
}
