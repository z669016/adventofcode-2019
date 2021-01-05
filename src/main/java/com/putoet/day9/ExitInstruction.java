package com.putoet.day9;

import java.util.Optional;

public class ExitInstruction extends Instruction {
    protected ExitInstruction(Registers regs) {
        super(regs, Operation.EXIT);
    }

    @Override
    public Optional<Registers> execute() {
        return Optional.of(registers());
    }
}
