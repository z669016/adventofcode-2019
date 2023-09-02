package com.putoet.day18;

import com.putoet.grid.Grid;
import com.putoet.grid.GridType;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;
import com.putoet.search.GenericSearch;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

class KeyMap {
    public static final char OPEN ='.';
    public static final char WALL ='#';
    public static final char ENTRANCE = '@';

    private final GridType grid;
    private Point entrance;

    public KeyMap(@NotNull GridType grid) {
        this.grid = grid;
    }

    public static KeyMap of(@NotNull List<String> lines) {
        return new KeyMap(new Grid(GridUtils.of(lines)));
    }

    private static boolean isDoor(char element) {
        return Character.isLetter(element) && Character.isUpperCase(element);
    }

    public static boolean isKey(char element) {
        return Character.isLetter(element) && Character.isLowerCase(element);
    }

    public static char asKey(char door) {
        return Character.toLowerCase(door);
    }

    private static boolean isOpen(char element) {
        return element == OPEN;
    }

    private static boolean isEntrance(char element) {
        return element == ENTRANCE;
    }

    public Point entrance() {
        if (this.entrance == null) {
            final Optional<Point> entrance = grid.findFirst(KeyMap::isEntrance);
            if (entrance.isEmpty())
                throw new IllegalStateException("Maze doesn't have an entry point.");

            this.entrance = entrance.get();
        }

        return this.entrance;
    }

    private Optional<Point> find(char id) {
        return grid.findFirst(c -> c == id);
    }

    public List<Pair<Character, Point>> keys() {
        return list('a', 'z');
    }

    public List<Pair<Character, Point>> doors() {
        return list('A', 'Z');
    }

    private List<Pair<Character, Point>> list(char lower, char upper) {
        final var points = new ArrayList<Pair<Character, Point>>();
        for (var id = lower; id <= upper; id++) {
            final var point = find(id);
            if (point.isPresent())
                points.add(new Pair<>(id, point.get()));
        }

        return points;
    }

    private Predicate<Point> goalTest(Predicate<Character> predicate) {
        return point -> predicate.test(grid.get(point.x(), point.y()));
    }

    private Function<Point, List<Point>> successors(Set<Character> possessedKeys) {
        return current -> {
            final var successors = new ArrayList<Point>();

            for (var move : List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST)) {
                final var successor = current.add(move);
                if (grid.contains(successor.x(), successor.y())) {
                    final var element = grid.get(successor.x(), successor.y());
                    if (isOpen(element) || isEntrance(element) || isKey(element)
                            || (isDoor(element) && possessedKeys.contains(asKey(element))))
                        successors.add(successor);
                }
            }

            return successors;
        };
    }

    public List<GenericSearch.Node<Point>> availableKeys(@NotNull Point from, @NotNull Set<Character> possessedKeys) {
        return GenericSearch.findAll(
                from,
                goalTest(c -> isKey(c) && !possessedKeys.contains(c)),
                successors(possessedKeys)
        );
    }

    public char elementAt(@NotNull Point point) {
        if (!contains(point))
            throw new IllegalArgumentException("Point " + point + " is not on the grid.");

        return grid.get(point.x(), point.y());
    }

    public SplitKeyMap split() {
        return new SplitKeyMap(grid.copy(), entrance());
    }

    public boolean contains(@NotNull Point point) {
        return grid.contains(point.x(), point.y());
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
