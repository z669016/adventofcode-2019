package com.putoet.intcode;

import com.putoet.resources.CSV;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntCodeDeviceTest {

    @Test
    void runDay2Sample() {
        final List<Integer> intCode = CSV.flatList("/day2.txt", Integer::parseInt);
        final com.putoet.intcode.Memory memory = new FixedMemory(intCode);
        final IntCodeDevice device = new IntCodeDevice(memory);

        device.run();
        assertEquals(3500, memory.peek(new Address(0)));
    }
}