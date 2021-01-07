package com.putoet.intcode;

import com.putoet.resources.CSV;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntCodeDeviceTest {

    @Test
    void runDay2Sample() {
        final List<Integer> intCode = CSV.flatList("/day2.txt", Integer::parseInt);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = new IntCodeDevice(memory);

        device.run();
        assertEquals(3500, memory.peek(new Address(0)));
    }

    @Test
    void runDay5Sample() {
        final List<Integer> intCode = List.of(1002,4,3,4,33);
        final Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = new IntCodeDevice(memory);
        device.run();
    }
}