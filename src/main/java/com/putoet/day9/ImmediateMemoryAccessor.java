package com.putoet.day9;

public class ImmediateMemoryAccessor implements IMemoryAccessor {
    private final Address address;
    private final IMemory memory;

    public ImmediateMemoryAccessor(Address address, IMemory memory) {
        this.address = address;
        this.memory = memory;
    }

    Address address() {
        return address;
    }

    @Override
    public Long peek() {
        return memory.peek(address);
    }

    @Override
    public void poke(Long value) {
        memory.poke(address, value);
    }

    @Override
    public String toString() {
        return String.format(" %s (<-- %s)", peek().toString(), address().toString());
    }
}
