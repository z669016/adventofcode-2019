package com.putoet.day15;

public enum Direction {
    NORTH,
    WEST,
    SOUTH,
    EAST;

    Direction left() {
        return leftOf(this);
    }


    Direction right() {
        return rightOf(this);
    }

    Direction leftOf(Direction direction) {
        switch (direction) {
            case NORTH:
                return EAST;
            case WEST:
                return NORTH;
            case SOUTH:
                return WEST;
            case EAST:
            default:
                return SOUTH;
        }
    }

    Direction rightOf(Direction direction) {
        switch (direction) {
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
            default:
                return NORTH;
        }
    }

    public Direction opposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case WEST:
                return EAST;
            case SOUTH:
                return NORTH;
            case EAST:
            default:
                return WEST;
        }
    }
}
