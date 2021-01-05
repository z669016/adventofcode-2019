package com.putoet.day5;

import java.util.List;
import java.util.Optional;

public interface IInputDevice {
    Optional<Integer> get();

    void put(Integer value);

    List<Integer> dump();
}
