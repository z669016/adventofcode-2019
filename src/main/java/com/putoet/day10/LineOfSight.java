package com.putoet.day10;

import java.util.*;

public class LineOfSight implements Comparable<LineOfSight> {
    private final Asteroid origin;
    private final Vector direction;
    private final TreeSet<Asteroid> hidden = new TreeSet<>(new Comparator<>() {
        @Override
        public int compare(Asteroid o1, Asteroid o2) {
            double distance1 = origin.location().euclideanDistance(o1.location());
            double distance2 = origin.location().euclideanDistance(o2.location());
            return Double.compare(distance1, distance2);
        }
    });
    private Asteroid visible;

    public LineOfSight(Asteroid origin, Vector direction, Asteroid visible) {
        this.origin = origin;
        this.visible = visible;
        this.direction = direction;
    }

    public static LineOfSight of(Asteroid origin, Asteroid visible) {
        return new LineOfSight(origin,
                Vector.ofPoints(origin.location(), visible.location()).direction(),
                visible);
    }

    private static double distanceTo(Asteroid a, Asteroid b) {
        return a.location().euclideanDistance(b.location());
    }

    public Asteroid origin() {
        return origin;
    }

    public Vector direction() {
        return direction;
    }

    public Optional<Asteroid> inLineOfSight() {
        return Optional.ofNullable(visible);
    }

    public Set<Asteroid> hidden() {
        return Collections.unmodifiableSet(hidden);
    }

    public void add(Asteroid asteroid) {
        if (origin.equals(asteroid))
            return;

        if (!direction.equals(Vector.ofPoints(origin.location(), asteroid.location()).direction()))
            return;

        if (distanceTo(origin, asteroid) < distanceTo(origin, visible)) {
            hidden.add(visible);
            visible = asteroid;
        } else {
            hidden.add(asteroid);
        }
    }

    @Override
    public String toString() {
        return String.format("LineOfSight {origin: %s, direction: %s, visible: %s, hidden: %s}", origin, direction, visible, hidden);
    }

    @Override
    public int compareTo(LineOfSight o) {
        return Double.compare(direction.degrees(), o.direction.degrees());
    }

    public Optional<Asteroid> vaporize() {
        final Optional<Asteroid> result = Optional.ofNullable(visible);

        if (result.isPresent()) {
            // System.out.println("Vaporized asteroid at " + result.get().location() + " at angle " + direction.degrees());

            try {
                final Asteroid head = hidden.first();
                hidden.remove(head);
                visible = head;
            } catch (NoSuchElementException exc) {
                visible = null;
            }
        }
        return result;
    }
}
