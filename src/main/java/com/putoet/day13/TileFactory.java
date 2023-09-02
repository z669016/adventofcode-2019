package com.putoet.day13;

class TileFactory {
    public static final Tile EMPTY = new Empty();
    public static final Tile WALL = new Wall();
    public static final Tile BLOCK = new Ball();
    public static final Tile BALL = new Ball();
    public static final Tile PADDLE = new Paddle();

    public static Tile of(int code) {
        return switch (code) {
            case 0 -> EMPTY;
            case 1 -> WALL;
            case 2 -> BLOCK;
            case 3 -> PADDLE;
            case 4 -> BALL;
            default -> throw new IllegalArgumentException("Invalid tile code: " + code);
        };
    }
}
