package com.putoet.day10;

import java.util.*;

public class LineOfSight {
    private final Astroid origin;
    private final Vector direction;
    private Astroid visible;
    private Set<Astroid> hidden = new HashSet<>();

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

    public Astroid inLineOfSight() {
        return visible;
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
}
