package com.putoet.day9;

import java.util.Optional;

public class SumInstruction extends TripleOperantInstruction {
    protected SumInstruction(Registers regs, IMemoryAccessor accessorA, IMemoryAccessor accessorB, IMemoryAccessor accessorC) {
        super(regs, Operation.SUM, accessorA, accessorB, accessorC);
    }

    @Override
    public Optional<Registers> execute() {
        c(a() + b());
        return super.execute();
    }
}
