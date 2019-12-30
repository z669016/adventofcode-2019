package com.putoet.day8;

import java.util.Objects;

public class Dimension {
    private final int x;
    private final int y;

    public static Dimension of(int x, int y) {
        assert (x > 0);
        assert (y > 0);

        return new Dimension(x, y);
    }

    private Dimension(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    public int size() { return x * y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dimension)) return false;
        Dimension dimension = (Dimension) o;
        return x == dimension.x &&
                y == dimension.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
