package com.putoet.day5;

import java.util.List;

public interface IOutputDevice {
    void put(Integer value);

    Integer get();

    List<Integer> dump();
}
