package com.putoet.day5;

import com.putoet.resources.CSV;

import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        final List<Integer> intCode = CSV.list("/day5.txt", Integer::parseInt).get(0);

        Processor.enableLog();
        InputDevice inputDevice = new InputDevice(List.of(1));
        OutputDevice outputDevice = new OutputDevice();

        // Part one
        Memory memory = Memory.of(intCode);
        Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        System.out.println("Available input for 5a is " + inputDevice);
        System.out.println("Available output for 5a is " + outputDevice);
        // System.out.println(processor.memory().dump());

        inputDevice = new InputDevice(List.of(5));
        outputDevice = new OutputDevice();

        // Part one
        memory = Memory.of(intCode);
        processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        System.out.println("Available input for 5b is " + inputDevice);
        System.out.println("Available output for 5b is " + outputDevice);
        // System.out.println(processor.memory().dump());
    }
}




