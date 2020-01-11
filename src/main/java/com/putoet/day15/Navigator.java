package com.putoet.day15;

import com.putoet.day11.Point;
import com.putoet.day9.IInputDevice;

import java.util.Collections;
import java.util.List;

public class Navigator {
    protected static final long NORTH = 1;
    protected static final long SOUTH = 2;
    protected static final long WEST = 3;
    protected static final long EAST = 4;

    private final IInputDevice inputDevice;
    private final List<Long> trace;
    private final ExtendableSurface surface;
    private Tile currentTile;
    private Point currentPoint;

    public Navigator(ExtendableSurface surface, IInputDevice inputDevice, List<Long> trace) {
        this.surface = surface;
        this.inputDevice = inputDevice;
        this.trace = trace;

        this.currentTile = Tile.START;
        currentPoint = new Point();
        this.surface.paint(currentPoint.x(), currentPoint.y(), currentTile);
    }

    public void north() {
        currentPoint.moveUp();
        currentTile.goNorth();
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

    private void openCurrentTile() {
        if (currentTile.type() == Tile.Type.UNKNOWN)
            currentTile.discovered(Tile.Type.OPEN);
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
