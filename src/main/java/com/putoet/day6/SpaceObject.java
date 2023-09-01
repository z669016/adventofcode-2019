package com.putoet.day6;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpaceObject {
    private final String name;
    private SpaceObject center;

    private static final SpaceObject COM = new SpaceObject();

    public static SpaceObject COM() {
        return COM;
    }

    private SpaceObject() {
        this.name = "COM";
        this.center = null;
    }

    public SpaceObject(@NotNull String name, @NotNull SpaceObject center) {
        assert !name.isEmpty();

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

    public void reCenter(@NotNull SpaceObject center) {
        if (this.center == center)
            return;

        if (this.center != COM)
            throw new IllegalStateException("Can only recenter from COM for " + this.name + " (current center is " + this.center.name + ")");

        this.center = center;
    }

    public static int orbitsToCom(@NotNull SpaceObject spaceObject) {
        if (spaceObject == COM)
            return 0;

        var count = 1;
        while (spaceObject.center != COM) {
            count++;
            spaceObject = spaceObject.center;
        }
        return count;
    }

    public static int orbitsTo(@NotNull SpaceObject from, @NotNull SpaceObject to) {
        if (from == COM)
            throw new IllegalArgumentException("Cannot count orbits from COM");

        if (from == to)
            return 0;

        var count = 0;
        var center = from.center;
        while (center != to && center != COM) {
            count++;
            center = center.center;
        }

        if (center == to)
            return count;

        throw new IllegalStateException(to.name + " is not at the route between " + from.name + " and COM");
    }

    public static List<SpaceObject> route(@NotNull SpaceObject so) {
        if (so == COM)
            return List.of();

        final var route = new ArrayList<SpaceObject>();
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
