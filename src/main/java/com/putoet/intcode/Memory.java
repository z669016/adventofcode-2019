package com.putoet.intcode;

import java.util.List;

public interface Memory {
    long peek(Address address);
    void poke(Address address, long value);
    int size();
    long[] toArray();
    List<Long> toList();
    Memory copy();

    class InvalidMemoryInitializer extends IllegalArgumentException {
        public InvalidMemoryInitializer(List<Long> memory) {
            super("Invalid memory initializer: " + memory);
        }
    }

    class InvalidMemoryAddress extends IllegalArgumentException {
        public InvalidMemoryAddress(Address address, int size) {
            super("Invalid memory address " + address + " for memory of size " + size);
        }
    }
}
