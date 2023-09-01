package com.putoet.day10;

import com.putoet.grid.Point;
import org.apache.commons.math3.util.ArithmeticUtils;

record Vector(int x, int y) implements Comparable<Vector> {

    public static Vector ofPoints(Point a, Point b) {
        return new Vector(b.x() - a.x(), b.y() - a.y());
    }

    public Vector direction() {
        final var gcd = ArithmeticUtils.gcd(x, y);
        return new Vector(x / gcd, y / gcd);
    }

    public double degrees() {
        final var degrees = Math.toDegrees(Math.atan2(x, y));
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
    public int compareTo(Vector o) {
        return Double.compare(degrees(), o.degrees());
    }
}
