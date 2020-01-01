package com.putoet.day10;

import java.util.Objects;

public class Astroid {
    private final Point location;

    public Astroid(Point location) {
        this.location = location;
    }

    public Point location() {
        return location;
    }

    public String name() {
        return String.format("A%02d%02d", location.x(), location.y());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Astroid)) return false;
        Astroid astroid = (Astroid) o;
        return Objects.equals(location, astroid.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return name();
    }
}
