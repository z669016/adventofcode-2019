package com.putoet.day9;

import java.util.List;

public interface IMemory {
    Long peek(Address address);
    void poke(Address address, Long value);

    int size();
    List<Long> asList();
}
