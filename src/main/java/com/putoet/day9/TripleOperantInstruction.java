package com.putoet.day9;

abstract class TripleOperantInstruction extends DoubleOperantInstruction {
    private final IMemoryAccessor accessor;

    protected TripleOperantInstruction(Registers regs, Operation operation, IMemoryAccessor accessorA, IMemoryAccessor accessorB, IMemoryAccessor accessorC) {
        super(regs, operation, accessorA, accessorB);

        if (accessorC == null)
            throw new IllegalArgumentException("Third accessor cannot be null");

        this.accessor = accessorC;
    }

    public final Long c() {
        return accessor.peek();
    }

    public final void c(Long value) {
        accessor.poke(value);
    }

    @Override
    public String toString() {
        return super.toString() + accessor.toString();
    }
}
