package com.putoet.day9;

public class PositionMemoryAccessor implements IMemoryAccessor {
    private final Address address;
    private final Address peekAddress;
    private final IMemory memory;

    public PositionMemoryAccessor(Address address, IMemory memory) {
        this.address = address;
        this.peekAddress = Address.of(memory.peek(address).intValue());
        this.memory = memory;
    }

    Address address() {
        return address;
    }

    Address peekAddress() {
        return peekAddress;
    }

    @Override
    public Long peek() {
        return memory.peek(peekAddress);
    }

    @Override
    public void poke(Long value) {
        memory.poke(peekAddress, value);
    }

    @Override
    public String toString() {
        return String.format(" %s (<-- %s <-- %s)", peek().toString(), peekAddress().toString(), address().toString());
    }
}
