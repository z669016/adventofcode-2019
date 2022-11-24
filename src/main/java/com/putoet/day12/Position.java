package com.putoet.day12;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Position {
    private static final Pattern pattern = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");

    public final int x;
    public final int y;
    public final int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Position of(String coordinates) {
        final Matcher matcher = pattern.matcher(coordinates);
        if (matcher.matches())
            return new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));

        throw new IllegalArgumentException("Invalid coordinates format('" + coordinates + "')");
    }

    public Position applyVelocity(Velocity velocity) {
        return new Position(x + velocity.x, y + velocity.y, z + velocity.z);
    }

    public int energy() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x &&
                y == position.y &&
                z == position.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("<x=%d, y=%d, z=%d>", x, y, z);
    }
}
