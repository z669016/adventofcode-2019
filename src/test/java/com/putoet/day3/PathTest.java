package com.putoet.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PathTest {

    @Test
    public void testPath() {
        assertThrows(AssertionError.class, () -> Path.of((String) null));
        assertThrows(AssertionError.class, () -> Path.of("q"));
        assertThrows(IllegalArgumentException.class, () -> Path.of("uu"));
        assertThrows(IllegalArgumentException.class, () -> Path.of("u-2"));

        Path path = Path.of("D");
        assertEquals(Direction.DOWN, path.direction());
        assertEquals(0, path.length());

        path = Path.of("U20");
        assertEquals(Direction.UP, path.direction());
        assertEquals(20, path.length());
    }
}
