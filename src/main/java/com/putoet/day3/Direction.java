package com.putoet.day3;

enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction of(Character c) {
        assert c != null;

        return switch (c) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("Not a direction: " + c);
        };
    }
}
