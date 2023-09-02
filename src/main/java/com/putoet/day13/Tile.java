package com.putoet.day13;

class Tile {
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
        return "█";
    }
}

class Ball extends Tile {
    @Override
    public String toString() {
        return "o";
    }
}

class Paddle extends Tile {
    @Override
    public String toString() {
        return "-";
    }
}
