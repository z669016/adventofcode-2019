package com.putoet.day12;

import com.putoet.resources.ResourceLines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoonMap {
    public static Map<String, Moon> loadFile(String fileName) {
        final List<String> moons = List.of("Io", "Europa", "Ganymede", "Callisto");
        final List<Position> positions = ResourceLines.stream(fileName)
                .map(Position::of)
                .collect(Collectors.toList());

        if (positions.size() != moons.size())
            throw new IllegalArgumentException("Number of provided positions (" + positions.size() + " doesn't match the number of moons (" + moons.size() + ")");

        final Map<String, Moon> moonMap = new HashMap<>();
        for (int idx = 0; idx < moons.size(); idx++) {
            final String name = moons.get(idx);
            moonMap.put(name, new Moon(name, positions.get(idx)));
        }

        return moonMap;
    }
}
