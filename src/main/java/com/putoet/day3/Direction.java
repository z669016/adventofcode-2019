package com.putoet.day3;

import com.putoet.grid.Point;

enum Direction {
    UP(Point.NORTH), DOWN(Point.SOUTH), LEFT(Point.WEST), RIGHT(Point.EAST);

    private final Point move;

    Direction(Point move) {
        this.move = move;
    }

    public static Direction of(Character c) {
        assert c != null;

        return switch (c) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new AssertionError(c);
        };
    }

    public Point asMove() {
        return move;
    }
}
