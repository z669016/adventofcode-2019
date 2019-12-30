package com.putoet.day9;

abstract class DoubleOperantInstruction extends SingeOperantInstruction {
    private final IMemoryAccessor accessor;

    protected DoubleOperantInstruction(Registers regs, Operation operation, IMemoryAccessor accessorA, IMemoryAccessor accessorB) {
        super(regs, operation, accessorA);

        if (accessorB == null)
            throw new IllegalArgumentException("Second accessor cannot be null");

        this.accessor = accessorB;
    }

    public final Long b() {
        return accessor.peek();
    }

    public final void b(Long value) {
        accessor.poke(value);
    }

    @Override
    public String toString() {
        return super.toString() + accessor.toString();
    }
}
