package com.putoet.day15;

public class Tile implements Paintable {
    private Tile north, east, south, west;
    private Type type = Type.UNKNOWN;

    public static final Tile START = new Tile(Type.START);

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
        if (this.type != Type.UNKNOWN)
            throw new IllegalStateException("Tile was already discovered ('" + this.paint() + "')");

        this.type = type;
    }

    public Tile north() { return north; }

    public Tile goNorth() {
        if (north == null) {
            north = new Tile(Type.UNKNOWN);
            north.south = this;
        }
        return north;
    }

    public Tile west() { return west; }

    public Tile goWest() {
        if (west == null) {
            west = new Tile(Type.UNKNOWN);
            west.east = this;
        }
        return west;
    }

    public Tile south() { return south; }

    public Tile goSouth() {
        if (south == null) {
            south = new Tile(Type.UNKNOWN);
            south.north = this;
        }
        return south;
    }

    public Tile east() { return east; }

    public Tile goEast() {
        if (east == null) {
            east = new Tile(Type.UNKNOWN);
            east.west = this;
        }
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
