package com.putoet.day10;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.*;

class SpaceArea {
    private final Map<Point, Asteroid> asteroids;

    public SpaceArea(@NotNull Map<Point, Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public static SpaceArea of(@NotNull List<String> lines) {
        if (lines.stream().noneMatch(s -> s != null && !s.isEmpty()))
            throw new IllegalArgumentException("Empty map");

        final var asteroids = new HashMap<Point, Asteroid>();
        for (var idy = 0; idy < lines.size(); idy++) {
            final var line = lines.get(idy);
            if (line != null) {
                for (var idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        final var location = Point.of(idx, idy);
                        asteroids.put(location, new Asteroid(location));
                    }
                }
            }
        }

        return new SpaceArea(asteroids);
    }

    public Collection<Asteroid> asteroids() {
        return asteroids.values();
    }

    public Map<Asteroid, LineOfSightMap> linesOfSightMaps() {
        final var map = new HashMap<Asteroid, LineOfSightMap>();
        asteroids.values().forEach(asteroid -> map.put(asteroid, LineOfSightMap.of(asteroid, asteroids.values())));
        return Collections.unmodifiableMap(map);
    }

    public Optional<Asteroid> asteroidAt(Point point) {
        return Optional.ofNullable(asteroids.get(point));
    }

    public Optional<LineOfSightMap> linesOfSightMapFor(Asteroid asteroid) {
        return Optional.ofNullable(asteroids.containsKey(asteroid.location()) ? LineOfSightMap.of(asteroid, asteroids.values()) : null);
    }
}
