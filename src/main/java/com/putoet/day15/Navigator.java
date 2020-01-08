package com.putoet.day15;

import com.putoet.day9.IInputDevice;

import java.util.Collections;
import java.util.List;

public class Navigator {
    public static final long NORTH = 1;
    public static final long SOUTH = 2;
    public static final long WEST = 3;
    public static final long EAST = 4;

    private final IInputDevice inputDevice;
    private final List<Long> trace;

    public Navigator(IInputDevice inputDevice, List<Long> trace) {
        this.inputDevice = inputDevice;
        this.trace = trace;
    }

    public void north() {
        inputDevice.put(NORTH);
        trace.add(NORTH);
    }

    public void south() {
        inputDevice.put(SOUTH);
        trace.add(SOUTH);
    }

    public void west() {
        inputDevice.put(WEST);
        trace.add(WEST);
    }

    public void east() {
        inputDevice.put(EAST);
        trace.add(EAST);
    }

    public void back() {
        trace.remove(trace.size() - 1);
    }

    public IInputDevice inputDevice() {
        return inputDevice;
    }

    public List<Long> trace() {
        return Collections.unmodifiableList(trace);
    }
}
