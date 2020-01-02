package com.putoet.day11;

import java.util.Objects;

public class Point {
    private final int x;
    private final int y;

    public Point() {
        x = y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point moveRight() {
        return new Point(x + 1, y);
    }

    public Point moveUp() {
        return new Point(x, y + 1);
    }

    public Point moveLeft() {
        return new Point(x - 1, y);
    }

    public Point moveDown() {
        return new Point(x, y - 1);
    }

    public int x() { return x; }

    public int y() { return y; }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
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

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
