package com.putoet.day9;

import java.util.Optional;

public class AdjustRelativeBaseInstruction extends SingeOperantInstruction {
    protected AdjustRelativeBaseInstruction(Registers regs, IMemoryAccessor accessorA) {
        super(regs, Operation.ADJUST_RELATIVE_BASE, accessorA);
    }

    @Override
    public Optional<Registers> execute() {
        final Long value = a();
        return Optional.of(registers().withIncreasedInstructionPointer(operation().size()).withIncreasedRelativeBase(value.intValue()));
    }
}
