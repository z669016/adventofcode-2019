package com.putoet.day15;

import com.putoet.day9.IOutputDevice;

import java.util.List;

public class Sensor {
    protected static final int BLOCKED = 0;
    protected static final int MOVED = 1;
    protected static final int HIT_AIR = 2;

    private final IOutputDevice outputDevice;

    public Sensor(IOutputDevice outputDevice) {
        this.outputDevice = outputDevice;
    }

    public State read() {
        final long intCode = outputDevice.get();
        if (!List.of((long)BLOCKED, (long)MOVED, (long)HIT_AIR).contains(intCode))
            throw new IllegalStateException("Invalid state from output device (" + intCode + ")");

        switch ((int) intCode) {
            case BLOCKED: return State.HIT_WALL;
            case MOVED: return State.MOVED;
            case HIT_AIR:
            default:
                return State.HIT_AIR;
        }
    }

    public enum State {
        HIT_WALL,
        MOVED,
        HIT_AIR
    }
}
