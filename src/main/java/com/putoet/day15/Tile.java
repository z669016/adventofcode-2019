package com.putoet.day15;

public class Tile implements Paintable {
    private Tile north, east, south, west;
    private Type type = Type.UNKNOWN;

    public static Tile START = new Tile(Type.START);

    private Tile(Type type) {
        this.type = type;
    }

    @Override
    public char paint() {
        return type.paint();
    }

    public Type type() {
        return type;
    }

    public void discovered(Type type) {
        this.type = type;
    }

    public Tile goNorth() {
        if (north == null)
            north = new Tile(Type.UNKNOWN);
        return north;
    }

    public Tile goWest() {
        if (west == null)
            west = new Tile(Type.UNKNOWN);
        return west;
    }

    public Tile goSouth() {
        if (south == null)
            south = new Tile(Type.UNKNOWN);
        return south;
    }

    public Tile goEast() {
        if (east == null)
            east = new Tile(Type.UNKNOWN);
        return east;
    }

    enum Type {
        UNKNOWN(' '),
        WALL('#'),
        OPEN('.'),
        START('.'),
        AIR('A');

        final char charToPaint;

        Type(char charToPaint) {
            this.charToPaint = charToPaint;
        }

        char paint() {
            return charToPaint;
        }
    }
}
