package com.putoet.day3;

import org.jetbrains.annotations.NotNull;

record Path(@NotNull Direction direction, int length) {
    public Path {
        if (length < 0)
            throw new IllegalArgumentException("Invalid length on path: " + length);
    }

    public static Path of(@NotNull String path) {
        assert !path.isEmpty();

        try {
            final var direction = Direction.of(path.toUpperCase().charAt(0));
            final var length = (path.length() > 1) ? Integer.parseInt(path.substring(1)) : 0;
            return new Path(direction, length);
        } catch (NumberFormatException exc) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
    }
}
