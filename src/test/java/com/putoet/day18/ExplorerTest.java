package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplorerTest {
    private static KeyMap keyMap(String resourceName) {
        final List<String> lines = ResourceLines.list(resourceName);
        return KeyMap.of(lines);
    }

    @Test
    void exploreSample1() {
        final KeyMap keyMap = keyMap("/day18-1.txt");
        assertEquals(8, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample2() {
        final KeyMap keyMap = keyMap("/day18-2.txt");
        assertEquals(86, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample3() {
        final KeyMap keyMap = keyMap("/day18-3.txt");
        assertEquals(132, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample4() {
        final KeyMap keyMap = keyMap("/day18-4.txt");
        final long start = System.currentTimeMillis();
        final int steps = Explorer.collectAllKeys(keyMap);
        System.out.println("Solved in " + (System.currentTimeMillis() - start) + " ms");
        assertEquals(136, steps);
    }

    @Test
    void exploreSample5() {
        final KeyMap keyMap = keyMap("/day18-5.txt");
        assertEquals(81, Explorer.collectAllKeys(keyMap));
    }
}