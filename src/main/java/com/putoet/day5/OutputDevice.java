package com.putoet.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutputDevice  implements Dump{
    private final List<Integer> outputValues = new ArrayList<>();

    public OutputDevice() {
    }

    public void put(Integer value) {
        outputValues.add(value);
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
