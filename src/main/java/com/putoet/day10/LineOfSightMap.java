package com.putoet.day10;

import java.util.*;
import java.util.stream.Collectors;

public class LineOfSightMap {
    private final Astroid origin;
    private final Map<Vector, LineOfSight> map = new HashMap<>();

    public LineOfSightMap(Astroid origin) {
        this.origin = origin;
    }

    public static LineOfSightMap of(Astroid origin, Set<Astroid> astroids) {
        final LineOfSightMap map = new LineOfSightMap(origin);
        astroids.forEach(map::add);
        return map;
    }

    public Set<LineOfSight> map() {
        return Collections.unmodifiableSet(new TreeSet<>(map.values()));
    }

    public void add(Astroid astroid) {
        if (origin.equals(astroid))
            return;

        final Vector direction = Vector.ofPoints(origin.location(), astroid.location()).direction();
        if (map.containsKey(direction))
            map.get(direction).add(astroid);
        else
            map.put(direction, LineOfSight.of(origin, astroid));
    }

    public int inLineOfSightCount() {
        return map.size();
    }

    public Set<Astroid> inLineOfSight() {
        return map.entrySet().stream()
                .map(entry -> entry.getValue().inLineOfSight())
                .filter(optional -> optional.isPresent())
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    public Set<Astroid> hidden() {
        return map.entrySet().stream().map(entry -> entry.getValue().hidden()).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(origin).append("\n");
        map.entrySet().forEach(entry -> sb.append(entry.getValue().toString()).append("\n"));
        return sb.toString();
    }
}
