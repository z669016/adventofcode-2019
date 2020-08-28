package com.putoet.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Memory {
    private final List<Integer> memory;

    private Memory(List<Integer> memory) {
        assert memory != null && memory.size() > 0;

        this.memory = new ArrayList<>(memory);
    }

    static Memory of(List<Integer> memory) {
        return new Memory(memory);
    }

    Integer peek(Address address) {
        checkAddress(address);
        return memory.get(address.toInt());
    }

    void poke(Address address, int value) {
        checkAddress(address);
        memory.set(address.toInt(), value);
    }

    int size() {
        return memory.size();
    }

    public String dump() {
        return memory.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private void checkAddress(Address address) {
        if (address.toInt() > memory.size() - 1)
            throw new IllegalArgumentException("Invalid memory address " + address.toInt());
    }
}
