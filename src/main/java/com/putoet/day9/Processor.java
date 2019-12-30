package com.putoet.day9;

import java.util.Optional;

public class Processor implements IProcessor {
    private final IMemory memory;
    private final IInputDevice inputDevice;
    private final IOutputDevice outputDevice;

    private Registers regs = new Registers();
    private boolean logEnabled = false;

    public Processor(IMemory memory, IInputDevice inputDevice, IOutputDevice outputDevice) {
        this.memory = memory;
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    @Override
    public IMemory memory() {
        return memory;
    }

    @Override
    public IInputDevice inputDevice() {
        return inputDevice;
    }

    @Override
    public IOutputDevice outputDevice() {
        return outputDevice;
    }

    @Override
    public void enableLog() {
        logEnabled = true;
    }

    @Override
    public void disableLog() {
        logEnabled = false;
    }

    @Override
    public void run() {
        Instruction instruction = InstructionFactory.of(regs, memory, inputDevice, outputDevice);
        while(instruction.operation() != Operation.EXIT) {
            try {
                final Optional<Registers> updated = instruction.execute();
                log(instruction.toString());

                updated.ifPresent(registers -> regs = registers);

                instruction = InstructionFactory.of(regs, memory, inputDevice, outputDevice);
            } catch (IllegalStateException | IllegalArgumentException exc) {
                System.err.println("Error occurred on IntCode program, with registers " + regs +" on instruction " + instruction);
                throw exc;
            }
        }
    }

    private void log(String line) {
        if (logEnabled)
            System.out.println(regs.toString() + " " + line);
    }
}
