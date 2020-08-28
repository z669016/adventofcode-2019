package com.putoet.day3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Path {
    private final Direction direction;
    private final int length;

    private Path(Direction direction, int length) {
        this.direction = direction;
        this.length = length;
    }

    public static Path of(String path) {
        assert path != null && path.length() > 0;

        try {
            final Direction direction = Direction.of(path.toUpperCase().charAt(0));
            final int length = (path.length() > 1) ? Integer.parseInt(path.substring(1)) : 0;

            if (length < 0)
                throw new IllegalArgumentException("Invalid length on path: " + path);

            return new Path(direction, length);
        } catch (NumberFormatException exc) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
    }

    public static List<Path> of(String... paths) {
        return Arrays.stream(paths).map(Path::of).collect(Collectors.toList());
    }

    int length() {
        return length;
    }

    Direction direction() {
        return direction;
    }

    @Override
    public String toString() {
        return String.format("%s %d", direction, length);
    }
}
