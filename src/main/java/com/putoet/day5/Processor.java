package com.putoet.day5;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    private final Memory memory;
    private final InputDevice inputDevice;
    private final OutputDevice outputDevice;

    private Address ip = Address.START_ADDRESS;

    public Processor(Memory memory, InputDevice inputDevice, OutputDevice outputDevice) {
        this.memory = memory;
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    public void run() {
        try {
            Instruction instruction = InstructionFactory.of(memory.peek(ip));
            while (instruction.operation() != Operation.EXIT) {
                ip = instruction.execute(ip, memory, inputDevice, outputDevice, operantsForInstruction(instruction, ip));
                instruction = InstructionFactory.of(memory.peek(ip));
            }
        } catch (IllegalArgumentException exc) {
            System.out.println("Execution failed at instruction " + ip + " (" + memory.peek(ip) + ")");
            System.out.println(exc);
            exc.printStackTrace();
        }
    }

    private Integer[] operantsForInstruction(Instruction instruction, Address address) {
        List<Integer> operants = new ArrayList<>();
        int size = instruction.size();
        while (--size > 0) {
            address = address.increase(1);
            operants.add(memory.peek(address));
        }

        return operants.toArray(new Integer[0]);
    }

    public Memory memory() {
        return memory;
    }

    public OutputDevice outputDevice() {
        return outputDevice;
    }

    public static final void enableLog() {
        Instruction.enableLog();
    }
    public static final void disableLog() {
        Instruction.disableLog();
    }
}
