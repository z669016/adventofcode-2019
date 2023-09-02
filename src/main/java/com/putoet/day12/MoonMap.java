package com.putoet.day12;

import com.putoet.resources.ResourceLines;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MoonMap {
    public static Map<String, Moon> loadFile(@NotNull String fileName) {
        final var moons = List.of("Io", "Europa", "Ganymede", "Callisto");
        final var positions = ResourceLines.stream(fileName)
                .map(Position::of)
                .toList();

        if (positions.size() != moons.size())
            throw new IllegalArgumentException("Number of provided positions (" + positions.size() + " doesn't match the number of moons (" + moons.size() + ")");

        final var moonMap = new HashMap<String, Moon>();
        for (var idx = 0; idx < moons.size(); idx++) {
            final var name = moons.get(idx);
            moonMap.put(name, new Moon(name, positions.get(idx)));
        }

        return moonMap;
    }
}
