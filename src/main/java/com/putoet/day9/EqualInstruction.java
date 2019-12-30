package com.putoet.day9;

import java.util.Optional;

public class EqualInstruction extends TripleOperantInstruction {
    protected EqualInstruction(Registers regs, IMemoryAccessor accessorA, IMemoryAccessor accessorB, IMemoryAccessor accessorC) {
        super(regs, Operation.EQUAL, accessorA, accessorB, accessorC);
    }

    @Override
    public Optional<Registers> execute() {
        c(a().equals(b()) ? 1L : 0L);

        return super.execute();
    }
}
