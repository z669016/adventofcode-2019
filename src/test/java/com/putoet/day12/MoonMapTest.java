package com.putoet.day12;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoonMapTest {

    @Test
    public void testLoadFile() {
        final Map<String,Moon> moons = MoonMap.loadFile("day12.txt");
        assertTrue(moons.containsKey("Io"));
        assertTrue(moons.containsKey("Europa"));
        assertTrue(moons.containsKey("Ganymede"));
        assertTrue(moons.containsKey("Callisto"));

        System.out.println(moons);
    }
}