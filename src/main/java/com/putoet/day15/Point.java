package com.putoet.day15;

import java.util.Objects;

public class Point {
    public static Point ORIGIN = new Point(0, 0);

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public int x() { return x; }
    public int y() { return y; }

    public Point relativeTo(Point origin) {
        return new Point(x + origin.x, y + origin.y);
    }

    public Point move(Direction direction) {
        switch (direction) {
            case NORTH:
                return new Point(x, y + 1);
            case WEST:
                return new Point(x + 1, y);
            case EAST:
                return new Point(x - 1, y);
            case SOUTH:
            default:
                return new Point(x, y - 1);
        }
    }

    public Point moveOpposite(Direction direction) {
        return move(direction.opposite());
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
