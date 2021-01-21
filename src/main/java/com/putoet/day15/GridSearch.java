package com.putoet.day15;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;

import java.util.List;
import java.util.Objects;

public class GridSearch {
    public static char WALL = '#';
    public static char OXYGEN_SYSTEM = 'O';
    public static char OPEN = '.';

    public static class State {
        public final Point point;
        public final char element;

        private State(Point point, char element) {
            this.point = point;
            this.element = element;
        }

        @Override
        public boolean equals(Object o) {
            if (this.element == WALL)
                return false;

            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State state = (State) o;
            return element == state.element && Objects.equals(point, state.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, element);
        }
    }

    private final Grid grid;

    public GridSearch(Grid grid) {
        this.grid = grid;
    }

    public State init(Point startingPoint) {
        return stateFor(startingPoint);
    }

    private State stateFor(Point point) {
        return new State(point, grid.get(point.x, point.y));
    }

    public List<State> wallSearch(State state) {
        return List.of(
                stateFor(state.point.add(Point.NORTH)),
                stateFor(state.point.add(Point.SOUTH)),
                stateFor(state.point.add(Point.WEST)),
                stateFor(state.point.add(Point.EAST))
        );
    }

    public List<State> oxygenSystemSearch(State state) {
        if (state.element == WALL)
            return List.of();

        return List.of(
                stateFor(state.point.add(Point.NORTH)),
                stateFor(state.point.add(Point.SOUTH)),
                stateFor(state.point.add(Point.WEST)),
                stateFor(state.point.add(Point.EAST))
        );
    }

    public boolean wallFound(State state) {
        return state.element == WALL;
    }

    public boolean oxygenSystemFound(State state) {
        return state.element == OXYGEN_SYSTEM;
    }
}
