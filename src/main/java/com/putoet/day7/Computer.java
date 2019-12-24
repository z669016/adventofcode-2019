package com.putoet.day7;

import com.putoet.day5.*;

import java.util.List;

public class Computer {
    private IInputDevice inputDevice;
    private OutputDevice outputDevice;
    private Memory memory;
    private Processor processor;

    public Computer(IInputDevice inputDevice, Memory memory) {
        this.inputDevice = inputDevice;
        this.outputDevice = new OutputDevice();
        this.memory = memory;
        this.processor = new Processor(memory, inputDevice, outputDevice);
    }

    public void run() {
        processor.run();
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

    public static void enableLog() {
        Processor.enableLog();
    }
    public static void disableLog() {
        Processor.disableLog();
    }

    public static ComputerBuilder builder() { return new ComputerBuilder(); }
    public static ComputerBuilder resumableBuilder() { return new ResumableComputerBuilder(); }

    static class ComputerBuilder {
        protected IInputDevice inputDevice;
        protected Memory memory;

        public ComputerBuilder() {
            inputDevice = null;
            memory = null;
        }

        public ComputerBuilder input(List<Integer> input) {
            if (this.inputDevice != null)
                throw new IllegalStateException("Input device already set");
            this.inputDevice =  new InputDevice(input);

            return this;
        }

        public final ComputerBuilder memory(List<Integer> memory) {
            if (this.memory != null)
                throw new IllegalStateException("Memory already set");
            this.memory = Memory.of(memory);

            return this;
        }

        public final Computer build() {
            if (inputDevice == null)
                throw new IllegalStateException("No input device  set");
            if (memory == null)
                throw new IllegalStateException("No memory set");

            return new Computer(inputDevice, memory);
        }
    }

    static class ResumableComputerBuilder extends ComputerBuilder {
        @Override
        public ComputerBuilder input(List<Integer> input) {
            if (this.inputDevice != null)
                throw new IllegalStateException("Input device already set");

            this.inputDevice =  new ResumableInputDevice(input);

            return this;
        }
    }
}
