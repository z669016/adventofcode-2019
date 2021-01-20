package com.putoet.day15;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;

import java.util.List;
import java.util.Objects;

public class GridSearch {
    public static class State {
        public final Point point;
        public final char element;

        private State(Point point, char element) {
            this.point = point;
            this.element = element;
        }

        @Override
        public boolean equals(Object o) {
            if (this.element == '#')
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
    private final Point oxygenSystem;

    public GridSearch(Grid grid, Point oxygenSystem) {
        this.grid = grid;
        this.oxygenSystem = oxygenSystem;
    }

    public State init() {
        return stateFor(oxygenSystem);
    }

    private State stateFor(Point point) {
        return new State(point, grid.get(point.x, point.y));
    }

    public List<State> successors(State state) {
        return List.of(
                stateFor(state.point.add(Point.NORTH)),
                stateFor(state.point.add(Point.SOUTH)),
                stateFor(state.point.add(Point.WEST)),
                stateFor(state.point.add(Point.EAST))
        );
    }

    public boolean hitWall(State state) {
        return state.element == '#';
    }
}
