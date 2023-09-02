package com.putoet.day20;

import com.putoet.grid.Point;
import com.putoet.search.GenericSearch;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class DonutMazeExplorer {
    private final DonutMaze maze;

    public DonutMazeExplorer(@NotNull DonutMaze maze) {
        this.maze = maze;
    }

    public int shortestRoute() {
        final var node = GenericSearch.bfs(init(), this::success, this::successors);
        return node.map(GenericSearch.Node::steps).orElse(-1);

    }

    public Point init() {
        return maze.entry();
    }

    public boolean success(@NotNull Point point) {
        return point.equals(maze.exit());
    }

    public List<Point> successors(@NotNull Point point) {
        final var successors = new ArrayList<Point>();
        final var adjacentList = DonutMaze.adjacent(point);

        for (var next : adjacentList) {
            if (isWall(next))
                continue;

            if (isOpen(next))
                successors.add(next);

            if (isGate(next)) {
                final var label = maze.labelAt(next);
                if (!DonutMaze.ENTRY.equals(label) && !DonutMaze.EXIT.equals(label)) {
                    final var gate = maze.gate(label);
                    if (gate == null)
                        throw new IllegalStateException("No gate found for label " + label);

                    successors.add(gate.getValue0().equals(point) ? gate.getValue1() : gate.getValue0());
                }
            }
        }

        return successors;
    }

    private boolean isWall(Point point) {
        return maze.tokenAt(point) == '#';
    }

    private boolean isOpen(Point point) {
        return maze.tokenAt(point) == '.';
    }

    private boolean isGate(Point point) {
        return Character.isLetter(maze.tokenAt(point));
    }
}
