package com.putoet.day9;

abstract class SingeOperantInstruction extends Instruction {
    private final IMemoryAccessor accessor;

    protected SingeOperantInstruction(Registers regs, Operation operation, IMemoryAccessor accessorA) {
        super(regs, operation);

        if (accessorA == null)
            throw new IllegalArgumentException("First accessor cannot be null");
        this.accessor = accessorA;
    }

    public final Long a() {
        return accessor.peek();
    }

    public final void a(Long value) {
        accessor.poke(value);
    }

    @Override
    public String toString() {
        return super.toString() + accessor.toString();
    }
}
