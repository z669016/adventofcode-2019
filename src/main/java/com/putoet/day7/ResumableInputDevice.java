package com.putoet.day7;

import com.putoet.day5.IInputDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResumableInputDevice implements IInputDevice {
    private final List<Integer> inputValues = new ArrayList<>();
    private int idx = 0;

    public ResumableInputDevice(List<Integer> inputValues) {
        assert (inputValues != null);

        this.inputValues.addAll(inputValues);
    }

    @Override
    public Optional<Integer> get() {
        if (idx >= inputValues.size())
            return Optional.empty();

        return Optional.of(inputValues.get(idx++));
    }

    @Override
    public void put(Integer value) {
        inputValues.add(value);
    }

    @Override
    public String toString() {
        return dump().toString();
    }

    @Override
    public List<Integer> dump() {
        return inputValues.stream().skip(idx).collect(Collectors.toUnmodifiableList());
    }}
