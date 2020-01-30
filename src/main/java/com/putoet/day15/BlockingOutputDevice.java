package com.putoet.day15;

import com.putoet.day14.Ingredient;
import com.putoet.day9.IDump;
import com.putoet.day9.IOutputDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockingOutputDevice implements IOutputDevice, IDump {
    private static final long SLEEP = 10;
    private final List<Long> outputValues = new ArrayList<>();
    private final Object host = new Object();
    private Integer readPointer = 0;

    public BlockingOutputDevice() {
    }

    @Override
    public void put(Long value) {
        synchronized (host) {
            outputValues.add(value);
        }
    }

    @Override
    public Long get() {
        try {
            while (outputValues.size() <= readPointer) Thread.sleep(SLEEP);
        } catch (InterruptedException exc) {

        }
        return outputValues.get(readPointer++);
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
