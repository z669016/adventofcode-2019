package com.putoet.day6;

import java.util.*;

public class SpaceMap {
    private final Map<String, SpaceObject> spaceObjects = new HashMap<>();

    public SpaceMap() {
        spaceObjects.put(SpaceObject.COM().name(), SpaceObject.COM());
    }

    public Map<String, SpaceObject> objects() {
        return Collections.unmodifiableMap(spaceObjects);
    }

    public void add(String spaceObjectName, SpaceObject center) {
        assert spaceObjectName != null;
        assert center != null;

        if (spaceObjects.containsKey(spaceObjectName)) {
            spaceObjects.get(spaceObjectName).reCenter(center);
        } else
            spaceObjects.put(spaceObjectName, new SpaceObject(spaceObjectName, center));
    }

    public SpaceObject get(String spaceObjectName) {
        assert spaceObjectName != null;

        return spaceObjects.get(spaceObjectName);
    }

    public int orbits() {
        return spaceObjects.values().stream().map(SpaceObject::orbitsToCom).reduce(0, Integer::sum);
    }

    public int distance(SpaceObject from, SpaceObject to) {
        assert from != null;
        assert to != null;

        final List<SpaceObject> routeFrom = SpaceObject.route(from);
        final List<SpaceObject> routeTo = SpaceObject.route(to);

        final Optional<SpaceObject> firstIntersection = routeFrom.stream().filter(routeTo::contains).findFirst();
        if (firstIntersection.isEmpty())
            throw new IllegalArgumentException("No intersection between the routes of " + from + " and " + to);

        return SpaceObject.orbitsTo(from, firstIntersection.get()) + SpaceObject.orbitsTo(to, firstIntersection.get());
    }
}
