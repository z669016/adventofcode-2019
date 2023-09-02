package com.putoet.day18;

import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class KeyMapTest {
    private KeyMap keyMap1;

    @BeforeEach
    void setup() {
        final var lines = ResourceLines.list("/day18-1.txt");
        keyMap1 = KeyMap.of(lines);
    }

    @Test
    void entrance() {
        assertEquals(Point.of(5,1), keyMap1.entrance());
    }

    @Test
    void keys() {
        final var keys = keyMap1.keys();
        assertEquals(2, keys.size());
        assertEquals(Set.of(new Pair<>('a', Point.of(7,1)), new Pair<>('b', Point.of(1,1))), Set.copyOf(keys));
    }

    @Test
    void doors() {
        final var doors = keyMap1.doors();
        assertEquals(1, doors.size());
        assertEquals(Set.of(new Pair<>('A', Point.of(3,1))), Set.copyOf(doors));
    }

    @Test
    void availableKeys() {
        var availableKeys = keyMap1.availableKeys(keyMap1.entrance(), Set.of());
        assertEquals(1, availableKeys.size());
        assertEquals(Point.of(7, 1), availableKeys.get(0).state);

        availableKeys = keyMap1.availableKeys(keyMap1.entrance(), Set.of('a'));
        assertEquals(1, availableKeys.size());
        assertEquals(Set.of(Point.of(1, 1)), availableKeys.stream().map(node -> node.state).collect(Collectors.toSet()));
    }
}