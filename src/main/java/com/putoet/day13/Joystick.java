package com.putoet.day13;

import com.putoet.day9.IInputDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Joystick implements IInputDevice {
    public static final long LEFT = -1;
    public static final long NEUTRAL = 0;
    public static final long RIGHT = 1;

    private final List<Long> input = new ArrayList<>();

    @Override
    public Optional<Long> get() {
        input.add(LEFT);
        return Optional.of(LEFT);
    }

    @Override
    public void put(Long value) {

    }

    public List<Long> dump() {
        return Collections.unmodifiableList(input);
    }
}
