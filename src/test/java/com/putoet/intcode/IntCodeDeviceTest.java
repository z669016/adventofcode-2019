package com.putoet.intcode;

import com.putoet.resources.CSV;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntCodeDeviceTest {

    @Test
    void runDay2Sample() {
        final List<Integer> intCode = CSV.flatList("/day2.txt", Integer::parseInt);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = new IntCodeComputer(memory);

        device.run();
        assertEquals(3500, memory.peek(new Address(0)));
    }

    @Test
    void runDay5ModeSample() {
        final List<Integer> intCode = List.of(1002,4,3,4,33);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = new IntCodeComputer(memory);
        device.run();
    }

    @Test
    void day5InOutSample(){
        final List<Integer> intCode = List.of(3,0,4,0,99);
        final Memory memory = new FixedMemory(intCode);
        final Queue<Long> input = new LinkedList<>();
        final Queue<Long> output = new LinkedList<>();
        final IntCodeDevice device = new IntCodeComputer(memory, input, output);

        input.offer(7L);
        device.run();
        assertEquals(1,output.size());
        assertEquals(7L, output.poll());
    }

    @Test
    void day5Part2Samples() {
        final List<Integer> intCode = List.of(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99);
        final Memory memory = new FixedMemory(intCode);
        final Queue<Long> input = new LinkedList<>();
        final Queue<Long> output = new LinkedList<>();
        final IntCodeDevice device = new IntCodeComputer(memory, input, output);

        input.offer(9L);
        device.run();
        assertEquals(1,output.size());
        assertEquals(1001L, output.poll());
    }
}