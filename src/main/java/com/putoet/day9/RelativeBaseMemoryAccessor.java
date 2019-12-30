package com.putoet.day9;

public class RelativeBaseMemoryAccessor implements IMemoryAccessor {
    private final Address relativeBaseAddress;
    private final Address offsetAddress;
    private final Address peekAddress;
    private final IMemory memory;

    public RelativeBaseMemoryAccessor(Address relativeBaseAddress, Address offsetAddress, IMemory memory) {
        this.relativeBaseAddress = relativeBaseAddress;
        this.offsetAddress = offsetAddress;
        this.peekAddress = relativeBaseAddress.increase(memory.peek(offsetAddress).intValue());
        this.memory = memory;
    }

    Address relativeBaseAddress() {
        return relativeBaseAddress;
    }

    Address offsetAddress() {
        return offsetAddress;
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
        return String.format(" %s (<-- %s <-- (%s + %s))",
                peek().toString(),
                peekAddress().toString(),
                relativeBaseAddress().toString(),
                memory.peek(offsetAddress()).toString());
    }
}
