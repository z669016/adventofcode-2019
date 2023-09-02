package com.putoet.day12;

import org.jetbrains.annotations.NotNull;

record Velocity(int x, int y, int z) {
    public Velocity applyGravity(@NotNull Position myPosition, @NotNull Position other) {
        return new Velocity(
                x + Integer.compare(other.x(), myPosition.x()),
                y + Integer.compare(other.y(), myPosition.y()),
                z + Integer.compare(other.z(), myPosition.z())
        );
    }

    public int energy() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }
}
