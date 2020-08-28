package com.putoet.day9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
    @Test
    public void testCopy() {
        final List<Integer> intCode = List.of(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99);
        final InputDevice inputDevice = new InputDevice(List.of());
        final OutputDevice outputDevice = new OutputDevice();
        final Memory memory = Memory.ofIntegerList(intCode);
        final Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        assertEquals(intCode, outputDevice.asList().stream().mapToInt(Long::intValue).boxed().collect(Collectors.toList()));
    }

    @Test
    public void testBigNumber() {
        final List<Integer> intCode = List.of(1102,34915192,34915192,7,4,7,99,0);
        final InputDevice inputDevice = new InputDevice(List.of());
        final OutputDevice outputDevice = new OutputDevice();
        final Memory memory = Memory.ofIntegerList(intCode);
        final Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        System.out.println("Big number is " + outputDevice.get());
        assertEquals(16, String.valueOf(outputDevice.get()).length());
    }

    @Test
    public void testVeryBigNumber() {
        final List<Long> intCode = List.of(104L,1125899906842624L,99L);
        final InputDevice inputDevice = new InputDevice(List.of());
        final OutputDevice outputDevice = new OutputDevice();
        final Memory memory = Memory.ofLongList(intCode);
        final Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        assertEquals(intCode.get(1), outputDevice.get());
    }
}
