package com.putoet.day12;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    @Test
    void repeat() {
        final Map<String, Moon> moons = MoonMap.loadFile("/day12.txt");
        assertEquals(2772L, Day12.repeat(moons));
    }
}