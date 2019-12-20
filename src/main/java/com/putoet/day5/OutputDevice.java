package com.putoet.day5;

import java.util.ArrayList;
import java.util.List;

public class OutputDevice {
    private final List<Integer> outputValues = new ArrayList<>();

    public OutputDevice() {
    }

    public void put(Integer value) {
        outputValues.add(value);
    }

    @Override
    public String toString() {
        return outputValues.toString();
    }
}
