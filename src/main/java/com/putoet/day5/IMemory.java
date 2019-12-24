package com.putoet.day5;

import java.util.List;

public interface IMemory {
    Integer peek(Address address);

    void poke(Address address, Integer value);

    int size();

    List<Integer> dump();
}
