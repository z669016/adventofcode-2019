package com.putoet.day13;

public class Tile {
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
        return "o" /*"\u26BD" */;
    }
}

class Paddle extends Tile {
    @Override
    public String toString() {
        return "-" /*"\uD83C\uDFD3"*/;
    }
}
