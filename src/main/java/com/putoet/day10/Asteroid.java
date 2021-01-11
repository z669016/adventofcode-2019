package com.putoet.day10;

import com.putoet.grid.Point;

import java.util.Objects;

public class Asteroid {
    private final Point location;

    public Asteroid(Point location) {
        this.location = location;
    }

    public Point location() {
        return location;
    }

    public String name() {
        return String.format("A%02d%02d", location.x, location.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asteroid)) return false;
        Asteroid asteroid = (Asteroid) o;
        return Objects.equals(location, asteroid.location);
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
