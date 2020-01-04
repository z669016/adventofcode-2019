package com.putoet.day12;

import java.util.Objects;

public class Velocity {
    private final int x;
    private final int y;
    private final int z;

    public Velocity(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public Velocity applyGravity(Position myPosition, Position other) {
        return new Velocity(
                x + Integer.compare(other.x(), myPosition.x()),
                y + Integer.compare(other.y(), myPosition.y()),
                z + Integer.compare(other.z(), myPosition.z())
        );
    }

    public int energy()  {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Velocity)) return false;
        Velocity velocity = (Velocity) o;
        return x == velocity.x &&
                y == velocity.y &&
                z == velocity.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("<x=%d, y=%d, z=%d>", x(), y(), z());
    }
}
