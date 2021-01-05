package com.putoet.day9;

import java.util.Optional;

abstract class Instruction {
    private final Registers regs;
    private final Operation operation;

    protected Instruction(Registers regs, Operation operation) {
        this.regs = regs;
        this.operation = operation;
    }

    public final Operation operation() {
        return operation;
    }


    public Optional<Registers> execute() {
        return Optional.of(regs.withIncreasedInstructionPointer(operation().size()));
    }

    public Registers registers() {
        return regs;
    }

    @Override
    public String toString() {
        return operation.toString();
    }
}
