package com.putoet.day9;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InputDevice implements IInputDevice {
    private final List<Long> inputValues;
    private int idx = 0;

    public InputDevice(List<Long> inputValues) {
        if (inputValues == null)
            throw new IllegalArgumentException("No inputvalues");

        this.inputValues = inputValues;
    }

    @Override
    public Optional<Long> get() {
        if (idx >= inputValues.size())
            throw new IllegalStateException("No input available");

        return Optional.of(inputValues.get(idx++));
    }

    @Override
    public void put(Long value) {
        throw new IllegalStateException("Cannot put into a normal input device");
    }

    @Override
    public String toString() {
        return asList().toString();
    }

    @Override
    public List<Long> asList() {
        return inputValues.stream().skip(idx).collect(Collectors.toUnmodifiableList());
    }
}
