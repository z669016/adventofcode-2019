package com.putoet.day18;

import com.putoet.grid.GridSection;
import com.putoet.grid.GridType;
import com.putoet.grid.Point;

import java.util.List;

public class SplitKeyMap {
    private final GridType grid;
    private final GridType[] sections;
    private final KeyMap keyMaps[];

    public SplitKeyMap(GridType grid, Point entrance) {
        this.grid = grid;

        grid.set(entrance.x, entrance.y, KeyMap.WALL);
        for (Point move : List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST)) {
            final Point point = entrance.add(move);
            grid.set(point.x, point.y, KeyMap.WALL);
        }

        for (Point move : List.of(Point.EAST, Point.WEST)) {
            final Point north = entrance.add(move).add(Point.NORTH);
            final Point south = entrance.add(move).add(Point.SOUTH);
            grid.set(north.x, north.y, KeyMap.ENTRANCE);
            grid.set(south.x, south.y, KeyMap.ENTRANCE);
        }

        this.sections = new GridType[]{
                new GridSection(grid, Point.of(0, 0), Point.of(entrance.x + 1, entrance.y + 1)),
                new GridSection(grid, Point.of(entrance.x, 0), Point.of(grid.maxX(), entrance.y + 1)),
                new GridSection(grid, Point.of(entrance.x, entrance.y), Point.of(grid.maxX(), grid.maxY())),
                new GridSection(grid, Point.of(0, entrance.y), Point.of(entrance.x + 1, grid.maxY()))
        };

        this.keyMaps = new KeyMap[] {
                new KeyMap(sections[0]),
                new KeyMap(sections[1]),
                new KeyMap(sections[2]),
                new KeyMap(sections[3])
        };
    }

    public KeyMap keyMap(int id) {
        assert id >= 0 && id < keyMaps.length;

        return keyMaps[id];
    }

    public KeyMap[] keyMaps() {
        return keyMaps;
    }
}
