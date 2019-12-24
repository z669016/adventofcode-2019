package com.putoet.day6;

import java.util.*;

public class SpaceMap {
    private Map<String, SpaceObject> spaceObjects = new HashMap<>();

    public SpaceMap() {
        spaceObjects.put(SpaceObject.COM().name(), SpaceObject.COM());
    }

    public Map<String, SpaceObject> objects() {
        return Collections.unmodifiableMap(spaceObjects);
    }

    public void add(String spaceObjectName, SpaceObject center) {
        if (spaceObjects.containsKey(spaceObjectName)) {
            spaceObjects.get(spaceObjectName).reCenter(center);
        } else
            spaceObjects.put(spaceObjectName, new SpaceObject(spaceObjectName, center));
    }

    public SpaceObject get(String spaceObjectName) {
        return spaceObjects.get(spaceObjectName);
    }

    public int orbits() {
        return spaceObjects.entrySet().stream().map(entry -> SpaceObject.orbitsToCom(entry.getValue())).reduce(0, Integer::sum);
    }

    public int distance(SpaceObject from, SpaceObject to) {
        final List<SpaceObject> routeFrom = SpaceObject.route(from);
        final List<SpaceObject> routeTo = SpaceObject.route(to);

        final Optional<SpaceObject> firstIntersection = routeFrom.stream().filter(so -> routeTo.contains(so)).findFirst();
        if (firstIntersection.isEmpty())
            throw new IllegalArgumentException("No intersection between the routes of " + from + " and " + to);

        return SpaceObject.orbitsTo(from, firstIntersection.get()) + SpaceObject.orbitsTo(to, firstIntersection.get());
    }
}
