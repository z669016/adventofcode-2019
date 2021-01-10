package com.putoet.day6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpaceObject {
    private final String name;
    private SpaceObject center;

    private static final SpaceObject COM = new SpaceObject();

    public static final SpaceObject COM() {
        return COM;
    }

    private SpaceObject() {
        this.name = "COM";
        this.center = null;
    }

    public SpaceObject(String name, SpaceObject center) {
        assert name != null && name.length() > 0;
        assert center != null;

        if (name.equals("COM"))
            throw new IllegalArgumentException("COM cannot be instantiated");

        this.name = name;
        this.center = center;
    }

    public String name() {
        return name;
    }

    public SpaceObject center() {
        return center;
    }

    public void reCenter(SpaceObject center) {
        assert center != null;

        if (this.center == center)
            return;

        if (this.center != COM)
            throw new IllegalStateException("Can only recenter from COM for " + this.name + " (current center is " + this.center.name + ")");

        this.center = center;
    }

    public static int orbitsToCom(SpaceObject spaceObject) {
        assert spaceObject != null;

        if (spaceObject == COM)
            return 0;

        int count = 1;
        while (spaceObject.center != COM) {
            count++;
            spaceObject = spaceObject.center;
        }
        return count;
    }

    public static int orbitsTo(SpaceObject from, SpaceObject to) {
        assert from != null;
        assert to != null;

        if (from == COM)
            throw new IllegalArgumentException("Cannot count orbits from COM");

        if (from == to)
            return 0;

        int count = 0;
        SpaceObject center = from.center;
        while (center != to && center != COM) {
            count++;
            center = center.center;
        }

        if (center == to)
            return count;

        throw new IllegalStateException(to.name + " is not at the route between " + from.name + " and COM");
    }

    public static List<SpaceObject> route(SpaceObject so) {
        assert so != null;

        if (so == COM)
            return List.of();

        final List<SpaceObject> route = new ArrayList<>();
        while (so != SpaceObject.COM()) {
            route.add(so);
            so = so.center();
        }

        return Collections.unmodifiableList(route);
    }

    @Override
    public String toString() {
        return (center == null ? "" : center.name) + ")" + name;
    }
}
