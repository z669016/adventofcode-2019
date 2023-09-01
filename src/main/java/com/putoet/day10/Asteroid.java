package com.putoet.day10;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

record Asteroid(@NotNull Point location) {

    public String name() {
        return String.format("A%02d%02d", location.x(), location.y());
    }

    @Override
    public String toString() {
        return name();
    }
}
