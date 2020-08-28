package com.putoet.day9;

import com.putoet.resources.CSV;

import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        final List<Integer> intCode = CSV.list("/day9.txt", Integer::parseInt).get(0);
        final Memory memory = Memory.ofIntegerList(intCode);

        InputDevice inputDevice = new InputDevice(List.of(1L));
        OutputDevice outputDevice = new OutputDevice();
        Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        System.out.println("Available output for 9a is " + outputDevice);

        inputDevice = new InputDevice(List.of(2L));
        outputDevice = new OutputDevice();
        processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        System.out.println("Available output for 9b is " + outputDevice);
    }
}