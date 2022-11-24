package com.putoet.day19;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;

public class TractorBeamMap {
    public static final char STATIONARY_TOKEN = '.';
    public static final char PULLED_TOKEN = '#';

    private final Grid grid;
    private final int minX;
    private final int maxX;

    public TractorBeamMap(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        grid = new Grid(minX, maxX, minY, maxY, GridUtils.of(minX, maxX, minY, maxY, '.'));
    }

    public static TractorBeamMap of(Drone drone, int maxX, int maxY) {
        return of (drone, 0, maxX, 0, maxY);
    }

    public static TractorBeamMap of(Drone drone, int minX, int maxX, int minY, int maxY) {
        final TractorBeamMap tractorBeamMap = new TractorBeamMap(minX, maxX, minY, maxY);

        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                final Point point = Point.of(x, y);
                final Drone.State state = drone.state(point);
                tractorBeamMap.grid.set(x, y, stateToChar(state));
            }
        }

        return tractorBeamMap;
    }

    public static char stateToChar(Drone.State state) {
        return switch (state) {
            case STATIONARY -> STATIONARY_TOKEN;
            case PULLED -> PULLED_TOKEN;
        };
    }

    public long affectedPoints() {
        return grid.count(PULLED_TOKEN);
    }

    public Point lowerVector(int y) {
        int x = minX;
        while (grid.get(x, y) == '.')
            x++;
        return Point.of(x, y);
    }

    public Point upperVector(int y) {
        int x = maxX - 1;
        while (grid.get(x, y) == '.')
            x--;
        return Point.of(x, y);
    }

    public boolean contains(Point point) {
        return grid.contains(point.x(), point.y());
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
