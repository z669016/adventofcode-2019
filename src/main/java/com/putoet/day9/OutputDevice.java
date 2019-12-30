package com.putoet.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutputDevice implements IOutputDevice {
    private final List<Long> outputValues = new ArrayList<>();

    public OutputDevice() {
    }

    @Override
    public void put(Long value) {
        outputValues.add(value);
    }

    @Override
    public Long get() {
        return outputValues.get(outputValues.size() - 1);
    }

    @Override
    public String toString() {
        return asList().toString();
    }

    @Override
    public List<Long> asList() {
        return Collections.unmodifiableList(outputValues);
    }
}
