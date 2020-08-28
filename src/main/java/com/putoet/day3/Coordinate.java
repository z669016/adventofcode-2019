package com.putoet.day3;

class Coordinate {
    private final int x, y;
    public static final Coordinate ORIGIN = of(0, 0);

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public Coordinate move(Direction direction) {
        return switch (direction) {
            case UP -> of(this.x, this.y + 1);
            case DOWN -> of(this.x, this.y - 1);
            case LEFT -> of(this.x - 1, this.y);
            default -> of(this.x + 1, this.y);
        };
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }
}
