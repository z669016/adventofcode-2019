package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplorerTest {
    private static KeyMap keyMap(String resourceName) {
        final var lines = ResourceLines.list(resourceName);
        return KeyMap.of(lines);
    }

    @Test
    void exploreSample1() {
        final var keyMap = keyMap("/day18-1.txt");
        assertEquals(8, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample2() {
        final var keyMap = keyMap("/day18-2.txt");
        assertEquals(86, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample3() {
        final var keyMap = keyMap("/day18-3.txt");
        assertEquals(132, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample4() {
        final var keyMap = keyMap("/day18-4.txt");
        assertEquals(136, Explorer.collectAllKeys(keyMap));
    }

    @Test
    void exploreSample5() {
        final var keyMap = keyMap("/day18-5.txt");
        assertEquals(81, Explorer.collectAllKeys(keyMap));
    }
}