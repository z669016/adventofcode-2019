package com.putoet.intcode;

import com.putoet.resources.CSV;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class IntCodeDeviceTest {

    @Test
    void runDay2Sample() {
        final List<Long> intCode = CSV.flatList("/day2.txt", Long::parseLong);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).build();

        device.run();
        assertEquals(3500L, memory.peek(new Address(0)));
    }

    @Test
    void runDay5ModeSample() {
        final List<Long> intCode = List.of(1002L, 4L, 3L, 4L, 33L);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).build();
        device.run();
    }

    @Test
    void day5InOutSample() {
        final List<Long> intCode = List.of(3L, 0L, 4L, 0L, 99L);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(7L);
        device.run();
        assertEquals(1L, output.size());
        assertEquals(7L, output.poll().orElseThrow());
    }

    @Test
    void day5BlockingInOutSample() {
        final List<Long> intCode = List.of(3L, 0L, 4L, 0L, 99L);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeConcurrentInputOutputDevice input = new IntCodeConcurrentInputOutputDevice();
        final IntCodeConcurrentInputOutputDevice output = new IntCodeConcurrentInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder()
                .memory(memory)
                .input(input)
                .output(output)
                .printStream(System.err)
                .timeout(1000, TimeUnit.MILLISECONDS)
                .build();

        final Thread thread = new Thread(device);
        thread.start();

        input.offer(7L);
        assertEquals(7L, output.poll(100, TimeUnit.MILLISECONDS).orElseThrow());
    }

    @Test
    void day5Part2Samples() {
        final List<Long> intCode = List.of(3L, 21L, 1008L, 21L, 8L, 20L, 1005L, 20L, 22L, 107L, 8L, 21L, 20L, 1006L, 20L, 31L,
                1106L, 0L, 36L, 98L, 0L, 0L, 1002L, 21L, 125L, 20L, 4L, 20L, 1105L, 1L, 46L, 104L,
                999L, 1105L, 1L, 46L, 1101L, 1000L, 1L, 20L, 4L, 20L, 1105L, 1L, 46L, 98L, 99L);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(9L);
        device.run();
        assertEquals(1L, output.size());
        assertEquals(1001L, output.poll().orElseThrow());
    }

    @Test
    void day9Part1Sample1() {
        final List<Long> intCode = List.of(109L, 1L, 204L, -1L, 1001L, 100L, 1L, 100L, 1008L, 100L, 16L, 101L, 1006L, 101L, 0L, 99L);
        final Memory memory = new ExpandableMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        device.run();

        assertEquals(intCode, output.asList());
    }

    @Test
    void day9Part1Sample2() {
        final List<Long> intCode = List.of(1102L, 34915192L, 34915192L, 7L, 4L, 7L, 99L, 0L);
        final Memory memory = new ExpandableMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        device.run();

        assertEquals(16L, String.valueOf(output.poll().orElseThrow()).length());
    }

    @Test
    void resumableTest() {
        final List<Long> intCode = List.of(3L, 5L, 4L, 5L, 99L, 0L);
        final Memory memory = new ExpandableMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).resumable().build();

        device.run();
        assertFalse(output.poll().isPresent());

        device.run();
        assertFalse(output.poll().isPresent());

        input.offer(11);
        device.run();
        assertEquals(11L, output.poll().getAsLong());
    }
}