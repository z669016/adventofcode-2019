package com.putoet.day7;

import com.putoet.day5.*;

import java.util.List;

public class Computer {
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    private Memory memory;
    private Processor processor;

    public Computer(InputDevice inputDevice, Memory memory) {
        this.inputDevice = inputDevice;
        this.outputDevice = new OutputDevice();
        this.memory = memory;
        this.processor = new Processor(memory, inputDevice, outputDevice);
    }

    public void run() {
        processor.run();;
    }

    public List<Integer> inputDump() {
        return inputDevice.dump();
    }

    public List<Integer> outputDump() {
        return outputDevice.dump();
    }

    public List<Integer> memoryDump() {
        return memory.dump();
    }

    public String intCodeDump() {
        return memory.toString();
    }

    public static final void enableLog() {
        Processor.enableLog();
    }
    public static final void disableLog() {
        Processor.disableLog();
    }
}
