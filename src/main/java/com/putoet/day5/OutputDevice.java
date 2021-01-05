package com.putoet.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutputDevice implements IDump, IOutputDevice {
    private final List<Integer> outputValues = new ArrayList<>();

    public OutputDevice() {
    }

    @Override
    public void put(Integer value) {
        outputValues.add(value);
    }

    @Override
    public Integer get() {
        return outputValues.get(outputValues.size() - 1);
    }

    @Override
    public String toString() {
        return dump().toString();
    }

    @Override
    public List<Integer> dump() {
        return Collections.unmodifiableList(outputValues);
    }
}
