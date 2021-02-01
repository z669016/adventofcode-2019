package com.putoet.day20;

import com.putoet.grid.Point;
import com.putoet.search.GenericSearch;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DonutMazeExplorer {
    private final DonutMaze maze;

    public DonutMazeExplorer(DonutMaze maze) {
        this.maze = maze;
    }

    public int shortestRoute() {
        final Optional<GenericSearch.Node<Point>> node = GenericSearch.bfs(init(), this::success, this::successors);
        if (node.isEmpty())
            return -1;

        return node.get().steps();
    }

    public Point init() {
        return maze.entry();
    }

    public boolean success(Point point) {
        return point.equals(maze.exit());
    }

    public List<Point> successors(Point point) {
        final List<Point> successors = new ArrayList<>();
        final List<Point> adjacents = DonutMaze.adjacent(point);

        for (Point next : adjacents) {
            if (isWall(next))
                continue;

            if (isOpen(next))
                successors.add(next);

            if (isGate(next)) {
                final String label = maze.labelAt(next);
                if (!DonutMaze.ENTRY.equals(label) && !DonutMaze.EXIT.equals(label)) {
                    final Pair<Point, Point> gate = maze.gate(label);
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
