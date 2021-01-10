package com.putoet.day8;

import java.util.Objects;

public class Size {
    private final int width;
    private final int height;

    public static Size of(int width, int height) {
        assert (width > 0);
        assert (height > 0);

        return new Size(width, height);
    }

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int size() {
        return width * height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Size)) return false;
        Size size = (Size) o;
        return width == size.width &&
                height == size.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
