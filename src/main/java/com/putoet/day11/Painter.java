package com.putoet.day11;

import com.putoet.day9.IDump;
import com.putoet.day9.IOutputDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Painter implements IOutputDevice, IDump {
    private final Surface surface;
    private final List<Long> instructions = new ArrayList<>();
    private boolean isPainting = true;

    public Painter(Surface surface) {
        this.surface = surface;
    }

    @Override
    public void put(Long value) {
        instructions.add(value);

        if (isPainting) {
            surface.paint(value == 0L ? Color.BLACK : Color.WHITE);
        } else {
            if (value == 0L)
                surface.turnRobotLeft();
            else
                surface.turnRobotRight();
            surface.moveRobot();
        }
        isPainting = !isPainting;
    }

    @Override
    public Long get() {
        return null;
    }

    @Override
    public List<Long> asList() {
        return Collections.unmodifiableList(instructions);
    }
}
