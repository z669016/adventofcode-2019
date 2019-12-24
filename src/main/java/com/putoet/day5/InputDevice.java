package com.putoet.day5;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InputDevice implements IDump, IInputDevice {
    private final List<Integer> inputValues;
    private int idx = 0;

    public InputDevice(List<Integer> inputValues) {
        assert (inputValues != null);

        this.inputValues = inputValues;
    }

    @Override
    public Optional<Integer> get() {
        if (idx >= inputValues.size())
            throw new IllegalStateException("No input available");

        return Optional.of(inputValues.get(idx++));
    }

    @Override
    public String toString() {
        return dump().toString();
    }

    @Override
    public List<Integer> dump() {
        return inputValues.stream().skip(idx).collect(Collectors.toUnmodifiableList());
    }
}

