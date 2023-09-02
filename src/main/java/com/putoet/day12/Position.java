package com.putoet.day12;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

record Position(int x, int y, int z) {
    private static final Pattern pattern = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");

    public static Position of(String coordinates) {
        final var matcher = pattern.matcher(coordinates);
        if (matcher.matches())
            return new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));

        throw new IllegalArgumentException("Invalid coordinates format('" + coordinates + "')");
    }

    public Position applyVelocity(@NotNull Velocity velocity) {
        return new Position(x + velocity.x(), y + velocity.y(), z + velocity.z());
    }

    public int energy() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }
}
