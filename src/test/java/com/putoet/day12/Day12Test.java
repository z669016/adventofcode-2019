package com.putoet.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    @Test
    void repeat() {
        final var moons = MoonMap.loadFile("/day12.txt");
        assertEquals(2772L, Day12.repeat(moons));
    }
}