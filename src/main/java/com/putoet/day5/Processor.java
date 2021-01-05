package com.putoet.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Processor {
    public enum State {
        CREATED,
        RUNNABLE,
        RUNNING,
        EXITED
    }

    private final Memory memory;
    private final IInputDevice inputDevice;
    private final OutputDevice outputDevice;
    private State state = State.CREATED;

    private Address ip = Address.START_ADDRESS;

    public Processor(Memory memory, IInputDevice inputDevice, OutputDevice outputDevice) {
        this.memory = memory;
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        state = State.RUNNABLE;
    }

    public void run() {
        state = State.RUNNING;
        try {
            Instruction instruction = InstructionFactory.of(memory.peek(ip));
            while (instruction.operation() != Operation.EXIT) {
                Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operantsForInstruction(instruction, ip));
                if (address.isEmpty()) {
                    state = State.RUNNABLE;
                    return;
                }

                ip = address.get();
                instruction = InstructionFactory.of(memory.peek(ip));
            }
        } catch (IllegalArgumentException exc) {
            System.out.println("Execution failed at instruction " + ip + " (" + memory.peek(ip) + ")");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }

        state = State.EXITED;
    }

    public State state() {
        return state;
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

    public static void enableLog() {
        Instruction.enableLog();
    }

    public static void disableLog() {
        Instruction.disableLog();
    }
}
