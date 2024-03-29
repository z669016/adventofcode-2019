package com.putoet.day10;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

class LineOfSightMap {
    private final Asteroid origin;
    private final Map<Vector, LineOfSight> map = new HashMap<>();

    public LineOfSightMap(Asteroid origin) {
        this.origin = origin;
    }

    public static LineOfSightMap of(@NotNull Asteroid origin, @NotNull Collection<Asteroid> asteroids) {
        final var map = new LineOfSightMap(origin);
        asteroids.forEach(map::add);
        return map;
    }

    public Set<LineOfSight> map() {
        return Collections.unmodifiableSet(new TreeSet<>(map.values()));
    }

    public void add(@NotNull Asteroid asteroid) {
        if (origin.equals(asteroid))
            return;

        final var direction = Vector.ofPoints(origin.location(), asteroid.location()).direction();
        if (map.containsKey(direction))
            map.get(direction).add(asteroid);
        else
            map.put(direction, LineOfSight.of(origin, asteroid));
    }

    public int inLineOfSightCount() {
        return map.size();
    }

    public Set<Asteroid> inLineOfSight() {
        return map.values().stream()
                .map(LineOfSight::inLineOfSight)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    public Set<Asteroid> hidden() {
        return map.values().stream().map(LineOfSight::hidden).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        sb.append(origin).append("\n");
        map.forEach((key, value) -> sb.append(value.toString()).append("\n"));
        return sb.toString();
    }
}
