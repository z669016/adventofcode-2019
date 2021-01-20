package com.putoet.intcode;

import java.util.List;

public class FixedMemory extends ExpandableMemory {
    public FixedMemory(List<Long> memory) {
        super(memory);
    }

    protected void checkAddress(Address address) {
        if (address.intValue() >= size())
            throw new Memory.InvalidMemoryAddress(address, size());
    }

    @Override
    public FixedMemory copy() {
        return new FixedMemory(toList());
    }
}

