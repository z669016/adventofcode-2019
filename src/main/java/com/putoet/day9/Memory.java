package com.putoet.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Memory implements IMemory {
    private List<Long> memory;

    private Memory(List<Long> memory) {
        if ((memory == null) || memory.size() == 0) throw new IllegalArgumentException("No memory");

        this.memory = new ArrayList<>(memory.size());
        this.memory.addAll(memory);
    }

    public static Memory ofIntegerList(List<Integer> memory) {
        if ((memory == null) || memory.size() == 0) throw new IllegalArgumentException("No memory");

        return new Memory(memory.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList()));
    }

    public static  Memory ofLongList(List<Long> memory) {
        return new Memory(memory);
    }

    @Override
    public Long peek(Address address) {
        checkAddress(address);
        return memory.get(address.offset());
    }

    @Override
    public void poke(Address address, Long value) {
        if (value == null) throw new IllegalArgumentException("Invalid memory write (null)");

        checkAddress(address);
        memory.set(address.offset(), value);
    }

    @Override
    public int size() {
        return memory.size();
    }

    @Override
    public List<Long> asList() {
        return Collections.unmodifiableList(memory);
    }

    private void checkAddress(Address address) {
        if (address.offset() > memory.size() - 1) {
            System.out.println("Enlarging memory to include address " + address);
            for (int idx = memory.size(); idx < address.increase(10).offset(); idx++) {
                memory.add(0L);
            }
        }
    }
}
