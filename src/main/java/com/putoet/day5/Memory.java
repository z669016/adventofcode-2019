package com.putoet.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Memory {
    private final List<Integer> memory;

    private Memory(List<Integer> memory) {
        this.memory = new ArrayList<Integer>();
        this.memory.addAll(memory);
    }

    static Memory of(List<Integer> memory) {
        if ((memory == null) || memory.size() == 0) throw new IllegalArgumentException("No memory");
        return new Memory(memory);
    }

    public Integer peek(Address address) {
        checkAddress(address);
        return memory.get(address.toInt());
    }

   public void poke(Address address, Integer value) {
        if (value == null) throw new IllegalArgumentException("Invalid memory write (null)");

        checkAddress(address);
        memory.set(address.toInt(), value);
    }

    public int size() {
        return memory.size();
    }

    public String dump() {
        return memory.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));
    }

    private void checkAddress(Address address) {
        if (address.toInt() > memory.size() - 1)
            throw new IllegalArgumentException("Invalid memory address " + address.toInt());
    }
}
