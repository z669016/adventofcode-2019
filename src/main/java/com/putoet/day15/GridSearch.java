package com.putoet.day15;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

class GridSearch {
    public static final char WALL = '#';
    public static final char OXYGEN_SYSTEM = 'O';
    public static final char OPEN = '.';

    public record State(@NotNull Point point, char element) {
        @Override
        public boolean equals(Object o) {
            if (this.element == WALL)
                return false;

            if (this == o) return true;
            if (!(o instanceof State state)) return false;
            return element == state.element && Objects.equals(point, state.point);
        }
    }

    private final Grid grid;

    public GridSearch(@NotNull Grid grid) {
        this.grid = grid;
    }

    public State init(@NotNull Point startingPoint) {
        return stateFor(startingPoint);
    }

    private State stateFor(@NotNull Point point) {
        return new State(point, grid.get(point.x(), point.y()));
    }

    public List<State> wallSearch(@NotNull State state) {
        return List.of(
                stateFor(state.point.add(Point.NORTH)),
                stateFor(state.point.add(Point.SOUTH)),
                stateFor(state.point.add(Point.WEST)),
                stateFor(state.point.add(Point.EAST))
        );
    }

    public List<State> oxygenSystemSearch(@NotNull State state) {
        if (state.element == WALL)
            return List.of();

        return List.of(
                stateFor(state.point.add(Point.NORTH)),
                stateFor(state.point.add(Point.SOUTH)),
                stateFor(state.point.add(Point.WEST)),
                stateFor(state.point.add(Point.EAST))
        );
    }

    public boolean wallFound(@NotNull State state) {
        return state.element == WALL;
    }

    public boolean oxygenSystemFound(@NotNull State state) {
        return state.element == OXYGEN_SYSTEM;
    }
}
