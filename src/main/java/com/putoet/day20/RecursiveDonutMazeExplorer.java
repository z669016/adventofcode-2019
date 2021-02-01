package com.putoet.day20;

import com.putoet.grid.Point;
import com.putoet.search.GenericSearch;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecursiveDonutMazeExplorer {
    private final DonutMaze maze;

    public RecursiveDonutMazeExplorer(DonutMaze maze) {
        this.maze = maze;
    }

    public int shortestRoute() {
        final Optional<GenericSearch.Node<Pair<Integer, Point>>> node = GenericSearch.bfs(init(), this::success, this::successors);
        if (node.isEmpty())
            return -1;

        return node.get().steps();
    }

    public Pair<Integer, Point> init() {
        return Pair.with(0, maze.entry());
    }

    public boolean success(Pair<Integer, Point> pair) {
        return pair.getValue0() == 0 && pair.getValue1().equals(maze.exit());
    }

    public List<Pair<Integer, Point>> successors(Pair<Integer, Point> pair) {
        final int level = pair.getValue0();
        final Point point = pair.getValue1();
        final List<Pair<Integer, Point>> successors = new ArrayList<>();
        final List<Point> adjacents = DonutMaze.adjacent(point);

        for (Point next : adjacents) {
            if (isWall(next))
                continue;

            if (isOpen(next))
                successors.add(Pair.with(pair.getValue0(), next));

            if (isGate(next)) {
                final String label = maze.labelAt(next);

                // EXIT and ENTRY are only visible at the most outer maze (level == 0) otherwise treat them as walls
                if (level != 0 && (DonutMaze.ENTRY.equals(label) || DonutMaze.EXIT.equals(label)))
                    continue;

                // Ignore outer gates when you're at the outer level.
                if (level == 0 && isOuter(next))
                    continue;

                if (!DonutMaze.ENTRY.equals(label) && !DonutMaze.EXIT.equals(label)) {
                    final Pair<Point, Point> gate = maze.gate(label);
                    if (gate == null)
                        throw new IllegalStateException("No gate found for label " + label);

                    final int l = isOuterGate(next) ? level + 1 : level - 1;
                    final Point p = gate.getValue0().equals(point) ? gate.getValue1() : gate.getValue0();
                    successors.add(Pair.with(l, p));
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

    private boolean isOuterGate(Point point) {
        return Character.isLetter(maze.tokenAt(point)) && isOuter(point);
    }

    private boolean isOuter(Point point) {
        return point.y == 1 || point.y == maze.maxY() - 2 ||
                point.x == 1 || point.x == maze.maxX() - 2;
    }
}
