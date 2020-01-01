package com.putoet.day10;

import java.math.BigInteger;
import java.util.Objects;

public class Vector {
    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    public static Vector ofPoints(Point a, Point b) {
        return new Vector(b.x() - a.x(), b.y() - a.y());
    }

    public Vector direction() {
        final int ggd = ggd(x, y);
        return new Vector(x/ggd, y/ggd);
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2));
    }

    @Override
    public String toString() {
        return String.format("{%d,%d}", x, y);
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
}
