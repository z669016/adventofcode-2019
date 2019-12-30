package com.putoet.day9;

import java.util.Optional;

public class ProductInstruction extends TripleOperantInstruction {
    protected ProductInstruction(Registers regs, IMemoryAccessor accessorA, IMemoryAccessor accessorB, IMemoryAccessor accessorC) {
        super(regs, Operation.PRODUCT, accessorA, accessorB, accessorC);
    }

    @Override
    public Optional<Registers> execute() {
        c(a() * b());
        return super.execute();
    }
}
