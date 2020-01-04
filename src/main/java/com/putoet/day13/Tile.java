package com.putoet.day13;

public class Tile {
    public static final Tile EMPTY = new Empty();
    public static final Tile WALL = new Wall();
    public static final Tile BLOCK = new Ball();
    public static final Tile BALL = new Ball();
    public static final Tile PADDLE = new Paddle();

    public static Tile of(int code) {
        switch (code) {
            case 0: return EMPTY;
            case 1: return WALL;
            case 2: return BLOCK;
            case 3: return BALL;
            case 4: return PADDLE;
            default:
                throw new IllegalArgumentException("Invalid tile code: " + code);
        }
    }
}

class Empty extends Tile {
    @Override
    public String toString() {
        return " ";
    }
}

class Wall extends Tile {
    @Override
    public String toString() {
        return "\u2588";
    }
}

class Block extends Tile {
    @Override
    public String toString() {
        return "\u2800";
    }
}

class Ball extends Tile {
    @Override
    public String toString() {
        return 	"o" /*"\u26BD" */;
    }
}

class Paddle extends Tile {
    @Override
    public String toString() {
        return 	"-" /*"\uD83C\uDFD3"*/ ;
    }
}
