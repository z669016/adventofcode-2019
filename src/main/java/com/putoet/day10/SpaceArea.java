package com.putoet.day10;

import com.putoet.grid.Point;

import java.util.*;

public class SpaceArea {
    private final Map<Point, Asteroid> asteroids;

    public SpaceArea(Map<Point, Asteroid> asteroids) {
        assert asteroids != null;

        this.asteroids = asteroids;
    }

    public static SpaceArea of(List<String> lines) {
        assert lines != null;

        if (lines.stream().noneMatch(s -> s != null && s.length() > 0))
            throw new IllegalArgumentException("Empty map");

        final Map<Point, Asteroid> asteroids = new HashMap<>();
        for (int idy = 0; idy < lines.size(); idy++) {
            final String line = lines.get(idy);
            if (line != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        final Point location = Point.of(idx, idy);
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
        final Map<Asteroid, LineOfSightMap> map = new HashMap<>();
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
