package com.putoet.day19;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

class TractorBeamMap {
    public static final char STATIONARY_TOKEN = '.';
    public static final char PULLED_TOKEN = '#';

    private final Grid grid;

    public TractorBeamMap(int minX, int maxX, int minY, int maxY) {
        grid = new Grid(minX, maxX, minY, maxY, GridUtils.of(minX, maxX, minY, maxY, '.'));
    }

    public static TractorBeamMap of(Drone drone, int maxX, int maxY) {
        return of (drone, 0, maxX, 0, maxY);
    }

    public static TractorBeamMap of(Drone drone, int minX, int maxX, int minY, int maxY) {
        final var tractorBeamMap = new TractorBeamMap(minX, maxX, minY, maxY);

        for (var y = minY; y < maxY; y++) {
            for (var x = minX; x < maxX; x++) {
                final var point = Point.of(x, y);
                final var state = drone.state(point);
                tractorBeamMap.grid.set(x, y, stateToChar(state));
            }
        }

        return tractorBeamMap;
    }

    public static char stateToChar(@NotNull Drone.State state) {
        return switch (state) {
            case STATIONARY -> STATIONARY_TOKEN;
            case PULLED -> PULLED_TOKEN;
        };
    }

    public long affectedPoints() {
        return grid.count(PULLED_TOKEN);
    }

    public boolean contains(@NotNull Point point) {
        return grid.contains(point.x(), point.y());
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
