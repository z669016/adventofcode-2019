package com.putoet.day15;

import java.util.*;

public class Tile implements Paintable {
    public static final Tile START = new Tile(Type.START);
    private Tile north, east, south, west;
    private Type type = Type.UNKNOWN;
    private boolean isCurrent = false;

    private Tile(Type type) {
        this.type = type;
    }

    @Override
    public char paint() {
        return isCurrent ? '@' : type.paint();
    }

    public Type type() {
        return type;
    }

    public void discovered(Type type) {
        if ((this.type != Type.UNKNOWN) && (type != Type.BLOCKED))
            throw new IllegalStateException("Tile was already discovered ('" + this.paint() + "')");

        this.type = type;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Optional<Tile> get(Direction direction) {
        switch (direction) {
            case NORTH:
                return Optional.ofNullable(north);
            case WEST:
                return Optional.ofNullable(west);
            case SOUTH:
                return Optional.ofNullable(south);
            case EAST:
            default:
                return Optional.ofNullable(east);
        }
    }

    public Tile move(Direction direction) {
        switch (direction) {
            case NORTH:
                return goNorth();
            case WEST:
                return goWest();
            case SOUTH:
                return goSouth();
            case EAST:
            default:
                return goEast();
        }
    }

    private Tile goNorth() {
        if (north == null) {
            north = new Tile(Type.UNKNOWN);
            north.south = this;
        }
        return north;
    }

    private Tile goWest() {
        if (west == null) {
            west = new Tile(Type.UNKNOWN);
            west.east = this;
        }
        return west;
    }

    private Tile goSouth() {
        if (south == null) {
            south = new Tile(Type.UNKNOWN);
            south.north = this;
        }
        return south;
    }

    private Tile goEast() {
        if (east == null) {
            east = new Tile(Type.UNKNOWN);
            east.west = this;
        }
        return east;
    }

    public boolean isDeadEnd() {
        final Tile[] tiles = {north, east, south, west};
        return Arrays.stream(tiles)
                .filter(Objects::nonNull)
                .filter(t -> t.type() == Type.WALL || t.type() == Type.BLOCKED)
                .count() == 3;
    }

    public boolean blockedOnDirection(Direction direction) {
        if (get(direction).isEmpty()) return false;

        final Tile tile = get(direction).get();
        if (tile.type() == Type.BLOCKED || tile.type() == Type.WALL) return true;

        return false;
    }

    enum Type {
        UNKNOWN(' '),
        WALL('#'),
        BLOCKED('B'),
        OPEN('.'),
        START('S'),
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
