package com.putoet.day10;

import java.util.*;

public class LineOfSight implements Comparable<LineOfSight> {
    private final Astroid origin;
    private final Vector direction;
    private Astroid visible;
    private TreeSet<Astroid> hidden = new TreeSet<>(new Comparator<Astroid>() {
        @Override
        public int compare(Astroid o1, Astroid o2) {
            double distance1 = Vector.ofPoints(origin.location(), o1.location()).length();
            double distance2 = Vector.ofPoints(origin.location(), o2.location()).length();
            return Double.compare(distance1, distance2);
        }
    });

    public LineOfSight(Astroid origin, Vector direction, Astroid visible) {
        this.origin = origin;
        this.visible = visible;
        this.direction = direction;
    }

    public static LineOfSight of(Astroid origin, Astroid visible) {
        return new LineOfSight(origin,
                Vector.ofPoints(origin.location(), visible.location()).direction(),
                visible);
    }

    public Astroid origin() {
        return origin;
    }

    public Vector direction() {
        return direction;
    }

    public Optional<Astroid> inLineOfSight() {
        return Optional.ofNullable(visible);
    }

    public Set<Astroid> hidden() {
        return Collections.unmodifiableSet(hidden);
    }

    public void add(Astroid astroid) {
        if (origin.equals(astroid))
            return;

        if (!direction.equals(Vector.ofPoints(origin.location(), astroid.location()).direction()))
            return;

        if (distanceTo(origin, astroid) < distanceTo(origin, visible)) {
            hidden.add(visible);
            visible = astroid;
        } else {
            hidden.add(astroid);
        }
    }

    private static double distanceTo(Astroid a, Astroid b) {
        return a.location().distanceTo(b.location());
    }

    @Override
    public String toString() {
        return String.format("LineOfSight {origin: %s, direction: %s, visible: %s, hidden: %s}", origin, direction, visible, hidden);
    }

    @Override
    public int compareTo(LineOfSight o) {
        return Double.compare(direction.degrees(), o.direction.degrees());
    }

    public Optional<Astroid> vaporize() {
        final Optional<Astroid> result = Optional.ofNullable(visible);

        if (result.isPresent()) {
            System.out.println("Vaporized astroid at " + result.get().location() + " at angle " + direction.degrees());

            try {
                final Astroid head = hidden.first();
                hidden.remove(head);
                visible = head;
            } catch (NoSuchElementException exc) {
                visible = null;
            }
        }
        return result;
    }
}
