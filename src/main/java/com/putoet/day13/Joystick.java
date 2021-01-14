package com.putoet.day13;

import com.putoet.intcode.InputDevice;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Joystick implements InputDevice {
    public static final int LEFT = -1;
    public static final int NEUTRAL = 0;
    public static final int RIGHT = 1;

    private int next = NEUTRAL;

    @Override
    public OptionalLong poll() {
        return OptionalLong.of(next);
    }

    @Override
    public OptionalLong poll(int timeout, TimeUnit timeUnit) {
        return OptionalLong.of(next);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        return switch (next) {
            case -1 -> "LEFT";
            case 0 -> "NEUTRAL";
            case 1 -> "RIGHT";
            default -> throw new IllegalStateException("Unexpected value: " + next);
        };
    }

    public void left() { next = LEFT; }
    public void right() { next = RIGHT; }
    public void neutral() { next= NEUTRAL; }
}
