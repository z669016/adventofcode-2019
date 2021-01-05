package com.putoet.day9;

import java.util.Optional;

public interface IInputDevice {
    Optional<Long> get();

    void put(Long value);
}
