package com.putoet.day9;

public interface IMemory {
    Long peek(Address address);

    void poke(Address address, Long value);

    int size();
}
