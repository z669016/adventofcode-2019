package com.putoet.day7;

import com.putoet.day5.InputDevice;
import com.putoet.day5.Memory;

import java.util.List;

public class ComputerBuilder {
    private InputDevice inputDevice;
    private Memory memory;

    public ComputerBuilder() {
        inputDevice = null;
        memory = null;
    }

    private ComputerBuilder(InputDevice inputDevice, Memory memory) {
        this.inputDevice = inputDevice;
        this.memory = memory;
    }

    public final ComputerBuilder input(List<Integer> input) {
        if (this.inputDevice != null)
            throw new IllegalStateException("Input device already set");

        return new ComputerBuilder(new InputDevice(input), memory);
    }

    public final ComputerBuilder memory(List<Integer> memory) {
        if (this.memory != null)
            throw new IllegalStateException("Memory already set");

        return new ComputerBuilder(inputDevice, Memory.of(memory));
    }

    public final Computer build() {
        if (inputDevice == null)
            throw new IllegalStateException("No input device  set");
        if (memory == null)
            throw new IllegalStateException("No memory set");

        return new Computer(inputDevice, memory);
    }
}
