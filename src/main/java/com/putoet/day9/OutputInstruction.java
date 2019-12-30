package com.putoet.day9;

import java.util.Optional;

public class OutputInstruction extends SingeOperantInstruction {
    private final IOutputDevice outputDevice;

    protected OutputInstruction(Registers regs, IMemoryAccessor accessorA, IOutputDevice outputDevice) {
        super(regs, Operation.OUTPUT, accessorA);
        this.outputDevice = outputDevice;
    }

    @Override
    public Optional<Registers> execute() {
        final Long value = a();
        outputDevice.put(value);

        return super.execute();
    }
}
