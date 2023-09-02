package com.putoet.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MoonMapTest {

    @Test
    public void testLoadFile() {
        final var moons = MoonMap.loadFile("/day12.txt");
        assertTrue(moons.containsKey("Io"));
        assertTrue(moons.containsKey("Europa"));
        assertTrue(moons.containsKey("Ganymede"));
        assertTrue(moons.containsKey("Callisto"));
    }
}