package com.putoet.intcode;

import java.util.List;

public class FixedMemory extends ExpandableMemory {
    public FixedMemory(List<Integer> memory) {
        super(memory);
    }

    protected void checkAddress(Address address) {
        if (address.intValue() >= size())
            throw new Memory.InvalidMemoryAddress(address, size());
    }
}

