package com.putoet.day5;

import java.util.List;
import java.util.stream.Collectors;

public class InputDevice {
    private final List<Integer> inputValues;
    private int idx = 0;

    public InputDevice(List<Integer> inputValues) {
        assert (inputValues != null);

        this.inputValues = inputValues;
    }

    public Integer get() {
        if (idx >= inputValues.size())
            throw new IllegalStateException("No input available");

        return inputValues.get(idx++);
    }

    @Override
    public String toString() {
        return inputValues.stream().skip(idx).collect(Collectors.toList()).toString();
    }
}

