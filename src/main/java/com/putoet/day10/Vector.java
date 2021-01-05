package com.putoet.day10;

import java.math.BigInteger;
import java.util.Objects;

public class Vector implements Comparable<Vector> {
    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public static Vector ofPoints(Point a, Point b) {
        return new Vector(b.x() - a.x(), b.y() - a.y());
    }

    public Vector direction() {
        final int ggd = ggd(x, y);
        return new Vector(x / ggd, y / ggd);
    }

    public double degrees() {
        double degrees = Math.toDegrees(Math.atan2(x, y));
        return Math.abs(degrees - 180.0);
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public String toString() {
        return String.format("{%d,%d (%3.02f)}", x, y, degrees());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return x == vector.x && y == vector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private static int ggd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    @Override
    public int compareTo(Vector o) {
        return Double.compare(degrees(), o.degrees());
    }
}
