package com.putoet.day15;

import com.putoet.day9.IInputDevice;

import java.util.Collections;
import java.util.List;

public class Navigator {
    private final IInputDevice inputDevice;
    private final List<Direction> trace;
    private Tile currentTile;
    private Point currentPoint;

    public Navigator(IInputDevice inputDevice, List<Direction> trace) {
        this.inputDevice = inputDevice;
        this.trace = trace;
        this.currentPoint = Point.ORIGIN;
        makeTileCurrent(Tile.START);
    }

    private static long intCodeFor(Direction direction) {
        switch (direction) {
            case NORTH:
                return 1;
            case WEST:
                return 3;
            case SOUTH:
                return 2;
            case EAST:
            default:
                return 4;
        }
    }

    public void move(Direction direction) {
        moveUpdateCurrent(direction);
        trace.add(direction);
        inputDevice.put(intCodeFor(direction));
    }

    private void moveUpdateCurrent(Direction direction) {
        currentPoint = currentPoint.move(direction);
        makeTileCurrent(currentTile.move(direction));
    }

    public void back() {
        Direction lastMove = trace.get(trace.size() - 1);
        trace.remove(trace.size() - 1);
        moveUpdateCurrent(lastMove.opposite());
    }

    public Direction lastDirection() {
        return trace.get(trace.size() - 1);
    }

    private void makeTileCurrent(Tile newCurrentTile) {
        if (currentTile != null) {
            currentTile.setCurrent(false);
        }
        currentTile = newCurrentTile;
        currentTile.setCurrent(true);
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

    public List<Direction> trace() {
        return Collections.unmodifiableList(trace);
    }
}
