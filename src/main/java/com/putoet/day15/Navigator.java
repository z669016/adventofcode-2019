package com.putoet.day15;

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
    private Tile currentTile;
    private Point currentPoint;

    public Navigator(IInputDevice inputDevice, List<Long> trace) {
        this.inputDevice = inputDevice;
        this.trace = trace;

        this.currentTile = Tile.START;
        this.currentPoint = Point.ORIGIN;
    }

    public void north() {
        currentPoint = currentPoint.moveNorth();
        currentTile = currentTile.goNorth();

        inputDevice.put(NORTH);
        trace.add(NORTH);
    }

    public void south() {
        currentPoint = currentPoint.moveSouth();
        currentTile = currentTile.goSouth();

        inputDevice.put(SOUTH);
        trace.add(SOUTH);
    }

    public void west() {
        currentPoint = currentPoint.moveWest();
        currentTile = currentTile.goWest();

        inputDevice.put(WEST);
        trace.add(WEST);
    }

    public void east() {
        currentPoint = currentPoint.moveEast();
        currentTile = currentTile.goEast();

        inputDevice.put(EAST);
        trace.add(EAST);
    }

    public void back() {
        long lastMove = trace.get(trace.size() - 1);
        trace.remove(trace.size() - 1);

        if (lastMove == NORTH) {
            currentPoint = currentPoint.moveSouth();
            currentTile = currentTile.south();
        } else if (lastMove == WEST) {
            currentPoint = currentPoint.moveEast();
            currentTile = currentTile.east();
        } else if (lastMove == SOUTH) {
            currentPoint = currentPoint.moveNorth();
            currentTile = currentTile.north();
        } else if (lastMove == EAST) {
            currentPoint = currentPoint.moveWest();
            currentTile = currentTile.west();
        }
    }

    public Point currentPoint() {
        return this.currentPoint;
    }

    public Tile currentTile() {
        return this.currentTile;
    }

    public IInputDevice inputDevice() {
        return inputDevice;
    }

    public List<Long> trace() {
        return Collections.unmodifiableList(trace);
    }
}
