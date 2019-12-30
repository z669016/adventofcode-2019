package com.putoet.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResumableInputDevice implements IInputDevice {
    private final List<Long> inputValues = new ArrayList<>();
    private int idx = 0;

    public ResumableInputDevice(List<Long> inputValues) {
        assert (inputValues != null);

        this.inputValues.addAll(inputValues);
    }

    @Override
    public Optional<Long> get() {
        if (idx >= inputValues.size())
            return Optional.empty();

        return Optional.of(inputValues.get(idx++));
    }

    @Override
    public void put(Long value) {
        inputValues.add(value);
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
