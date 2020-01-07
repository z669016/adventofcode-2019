package com.putoet.day13;

import com.putoet.day9.IOutputDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen implements IOutputDevice {
    private final List<String> output = new ArrayList<>();
    private final ExtendableSurface surface = new ExtendableSurface();
    private int x;
    private int y;
    private State state = State.X;
    private long score;

    enum State {
        X, Y, TILE;
    }

    public long count(Tile tile) {
        return surface.count(tile);
    }

    @Override
    public void put(Long value) {
        output.add(state.toString() + "=" + value);
        switch (state) {
            case X:
                x = value.intValue();
                state = State.Y;
                break;
            case Y:
                y = value.intValue();
                state = State.TILE;
                break;
            case TILE:
                if (x == -1 && y == 0)
                    score = value;
                else
                    surface.paint(x, y, Tile.of(value.intValue()));
                state = State.X;
        }
    }

    @Override
    public Long get() {
        throw new IllegalStateException("Method not available for the Screen output device");
    }

    @Override
    public String toString() {
        return "SCORE " + score + "\n" + surface.toString();
    }

    public List<String> dump() {
        return Collections.unmodifiableList(output);
    }
}
