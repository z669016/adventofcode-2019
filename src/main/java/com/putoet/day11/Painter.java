package com.putoet.day11;

import com.putoet.intcode.OutputDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Painter implements OutputDevice {
    private final Surface surface;
    private final List<Long> instructions = new ArrayList<>();
    private boolean isPainting = true;

    public Painter(Surface surface) {
        this.surface = surface;
    }

    @Override
    public void offer(long value) {
        instructions.add(value);

        if (isPainting) {
            surface.paint(value == 0L ? PanelColor.BLACK : PanelColor.WHITE);
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
    public int size() {
        return instructions.size();
    }

    @Override
    public List<Long> asList() {
        return Collections.unmodifiableList(instructions);
    }
}
