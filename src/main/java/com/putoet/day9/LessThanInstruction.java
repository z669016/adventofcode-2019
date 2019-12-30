package com.putoet.day9;

import java.util.Optional;

public class LessThanInstruction extends TripleOperantInstruction {
    protected LessThanInstruction(Registers regs, IMemoryAccessor accessorA, IMemoryAccessor accessorB, IMemoryAccessor accessorC) {
        super(regs, Operation.LESS_THAN, accessorA, accessorB, accessorC);
    }

    @Override
    public Optional<Registers> execute() {
        c(a() < b() ? 1L : 0L);

        return super.execute();
    }
}
