package com.putoet.day9;

import java.util.Optional;

public class InputInstruction extends SingeOperantInstruction {
    private final IInputDevice inputDevice;
    protected InputInstruction(Registers regs, IMemoryAccessor accessorA, IInputDevice inputDevice) {
        super(regs, Operation.INPUT, accessorA);
        this.inputDevice = inputDevice;
    }

    @Override
    public Optional<Registers> execute() {
        final Optional<Long> value = inputDevice.get();
        if (value.isEmpty())
            return Optional.empty();

        a(value.get());

        return super.execute();
    }
}
