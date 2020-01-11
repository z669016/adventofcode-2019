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
        this.currentTile = Tile.START;
        this.currentPoint = Point.ORIGIN;
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
        moveUpdate(direction);
        trace.add(direction);
    }

    private void moveUpdate(Direction direction) {
        currentPoint = currentPoint.move(direction);
        currentTile = currentTile.move(direction);
        inputDevice.put(intCodeFor(direction));
    }

    public void back() {
        Direction lastMove = trace.get(trace.size() - 1);
        trace.remove(trace.size() - 1);
        moveUpdate(lastMove.opposite());
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
