package com.putoet.day18;

import com.putoet.grid.GridSection;
import com.putoet.grid.GridType;
import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class SplitKeyMap {
    private final KeyMap[] keyMaps;

    public SplitKeyMap(@NotNull GridType grid, @NotNull Point entrance) {

        grid.set(entrance.x(), entrance.y(), KeyMap.WALL);
        for (var move : List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST)) {
            final var point = entrance.add(move);
            grid.set(point.x(), point.y(), KeyMap.WALL);
        }

        for (var move : List.of(Point.EAST, Point.WEST)) {
            final var north = entrance.add(move).add(Point.NORTH);
            final var south = entrance.add(move).add(Point.SOUTH);
            grid.set(north.x(), north.y(), KeyMap.ENTRANCE);
            grid.set(south.x(), south.y(), KeyMap.ENTRANCE);
        }

        final var sections = new GridType[]{
                new GridSection(grid, Point.of(0, 0), Point.of(entrance.x() + 1, entrance.y() + 1)),
                new GridSection(grid, Point.of(entrance.x(), 0), Point.of(grid.maxX(), entrance.y() + 1)),
                new GridSection(grid, Point.of(entrance.x(), entrance.y()), Point.of(grid.maxX(), grid.maxY())),
                new GridSection(grid, Point.of(0, entrance.y()), Point.of(entrance.x() + 1, grid.maxY()))
        };

        this.keyMaps = new KeyMap[] {
                new KeyMap(sections[0]),
                new KeyMap(sections[1]),
                new KeyMap(sections[2]),
                new KeyMap(sections[3])
        };
    }

    public KeyMap[] keyMaps() {
        return keyMaps;
    }
}
