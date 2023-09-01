package com.putoet.day6;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SpaceMap {
    private final Map<String, SpaceObject> spaceObjects = new HashMap<>();

    public SpaceMap() {
        spaceObjects.put(SpaceObject.COM().name(), SpaceObject.COM());
    }

    public Map<String, SpaceObject> objects() {
        return Collections.unmodifiableMap(spaceObjects);
    }

    public void add(@NotNull String spaceObjectName, @NotNull SpaceObject center) {
        if (spaceObjects.containsKey(spaceObjectName)) {
            spaceObjects.get(spaceObjectName).reCenter(center);
        } else
            spaceObjects.put(spaceObjectName, new SpaceObject(spaceObjectName, center));
    }

    public SpaceObject get(@NotNull String spaceObjectName) {
        return spaceObjects.get(spaceObjectName);
    }

    public int orbits() {
        return spaceObjects.values().stream().map(SpaceObject::orbitsToCom).reduce(0, Integer::sum);
    }

    public int distance(@NotNull SpaceObject from, @NotNull SpaceObject to) {
        final var routeFrom = SpaceObject.route(from);
        final var routeTo = SpaceObject.route(to);
        final var firstIntersection = routeFrom.stream().filter(routeTo::contains).findFirst().orElseThrow();

        return SpaceObject.orbitsTo(from, firstIntersection) + SpaceObject.orbitsTo(to, firstIntersection);
    }
}
