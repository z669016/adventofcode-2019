package com.putoet.day11;

import com.putoet.grid.Point;

public enum Direction {
    UP('^'),
    RIGHT('>'),
    DOWN('v'),
    LEFT('<');

    private final char directionCharacter;

    Direction(char directionCharacter) {
        this.directionCharacter = directionCharacter;
    }

    public Point asMove() {
        return switch (this) {
            case UP -> Point.NORTH;
            case RIGHT -> Point.EAST;
            case DOWN -> Point.SOUTH;
            case LEFT -> Point.WEST;
        };
    }

    @Override
    public String toString() {
        return String.valueOf(directionCharacter);
    }
}
