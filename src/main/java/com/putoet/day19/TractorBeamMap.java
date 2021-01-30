package com.putoet.day19;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;

public class TractorBeamMap {
    public static final char STATIONARY_TOKEN = '.';
    public static final char PULLED_TOKEN = '#';

    private final Grid grid;
    private final int maxX;
    private final int maxY;

    public TractorBeamMap(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        grid = new Grid(GridUtils.of(0, maxX, 0, maxY, '.'));
    }

    public static TractorBeamMap of(Drone drone, int maxX, int maxY) {
        final TractorBeamMap tractorBeamMap = new TractorBeamMap(maxX, maxY);

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                final Point point = Point.of(x, y);
                final Drone.State state = drone.state(point);
                switch (state) {
                    case STATIONARY -> tractorBeamMap.grid.set(x, y, STATIONARY_TOKEN);
                    case PULLED -> tractorBeamMap.grid.set(x, y, PULLED_TOKEN);
                }
            }
        }

        return tractorBeamMap;
    }

    public long affectedPoints() {
        return grid.count(PULLED_TOKEN);
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
