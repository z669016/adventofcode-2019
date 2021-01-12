package com.putoet.day7;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmplifierArrayTest {

    @Test
    void sample1Part1() {
        final List<Long> intCodeProgram = List.of(3L, 15L, 3L, 16L, 1002L, 16L, 10L, 16L, 1L, 16L, 15L, 15L,
                4L, 15L, 99L, 0L, 0L);
        final PhaseSetting phaseSetting = new PhaseSetting(List.of(4, 3, 2, 1, 0));
        final AmplifierArray amplifierArray = new SimpleAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        final OptionalLong output = amplifierArray.output(100, TimeUnit.MILLISECONDS);
        assertTrue(output.isPresent());
        assertEquals(43210, output.getAsLong());
    }

    @Test
    void sample2Part1() {
        final List<Long> intCodeProgram = List.of(3L, 23L, 3L, 24L, 1002L, 24L, 10L, 24L, 1002L, 23L, -1L, 23L,
                101L, 5L, 23L, 23L, 1L, 24L, 23L, 23L, 4L, 23L, 99L, 0L, 0L);
        final PhaseSetting phaseSetting = new PhaseSetting(List.of(0, 1, 2, 3, 4));
        final AmplifierArray amplifierArray = new SimpleAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        final OptionalLong output = amplifierArray.output(100, TimeUnit.MILLISECONDS);
        assertTrue(output.isPresent());
        assertEquals(54321, output.getAsLong());
    }

    @Test
    void sample3Part1() {
        final List<Long> intCodeProgram = List.of(3L,31L,3L,32L,1002L,32L,10L,32L,1001L,31L,-2L,31L,1007L,31L,0L,33L,
                1002L,33L,7L,33L,1L,33L,31L,31L,1L,32L,31L,31L,4L,31L,99L,0L,0L,0L);
        final PhaseSetting phaseSetting = new PhaseSetting(List.of(1,0,4,3,2));
        final AmplifierArray amplifierArray = new SimpleAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        final OptionalLong output = amplifierArray.output(100, TimeUnit.MILLISECONDS);
        assertTrue(output.isPresent());
        assertEquals(65210, output.getAsLong());
    }

    @Test
    void sample1Part2() {
        final List<Long> intCodeProgram = List.of(3L,26L,1001L,26L,-4L,26L,3L,27L,1002L,27L,2L,27L,1L,27L,26L,
                27L,4L,27L,1001L,28L,-1L,28L,1005L,28L,6L,99L,0L,0L,5L);
        final PhaseSetting phaseSetting = new PhaseSetting(List.of(9,8,7,6,5));
        final AmplifierArray amplifierArray = new FeedbackAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        final OptionalLong output = amplifierArray.output(1, TimeUnit.SECONDS);
        assertTrue(output.isPresent());
        assertEquals(139629729, output.getAsLong());
    }

    @Test
    void sample2Part2() {
        final List<Long> intCodeProgram = List.of(3L,52L,1001L,52L,-5L,52L,3L,53L,1L,52L,56L,54L,1007L,54L,5L,55L,1005L,
                55L,26L,1001L,54L,-5L,54L,1105L,1L,12L,1L,53L,54L,53L,1008L,54L,0L,55L,1001L,55L,1L,55L,2L,53L,55L,53L,
                4L, 53L,1001L,56L,-1L,56L,1005L,56L,6L,99L,0L,0L,0L,0L,10L);
        final PhaseSetting phaseSetting = new PhaseSetting(List.of(9,7,8,5,6));
        final AmplifierArray amplifierArray = new FeedbackAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        final OptionalLong output = amplifierArray.output(1, TimeUnit.SECONDS);
        assertTrue(output.isPresent());
        assertEquals(18216, output.getAsLong());
    }
}