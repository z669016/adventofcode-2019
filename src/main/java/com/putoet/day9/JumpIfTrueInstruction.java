package com.putoet.day9;

import java.util.Optional;

public class JumpIfTrueInstruction extends DoubleOperantInstruction {
    protected JumpIfTrueInstruction(Registers regs, IMemoryAccessor accessorA, IMemoryAccessor accessorB) {
        super(regs, Operation.JUMP_IF_TRUE, accessorA, accessorB);
    }

    @Override
    public Optional<Registers> execute() {
        final Long value = a();
        if (value != 0)
            return Optional.of(new Registers(Address.of(b().intValue()), registers().rb()));

        return super.execute();
    }
}
