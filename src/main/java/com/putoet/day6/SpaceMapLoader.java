package com.putoet.day6;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpaceMapLoader {
    public static SpaceMap loadMap(@NotNull List<String> mapNotations) {
        final var map = new SpaceMap();
        mapNotations.forEach(notation -> createSpaceObjects(map, notation));
        return map;
    }

    private static void createSpaceObjects(SpaceMap map, String mapNotation) {
        validateNotation(mapNotation);

        final var centerName = mapNotation.substring(0, 3);
        final var spaceObjectName = mapNotation.substring(4);
        final var center = getOrCreateCenter(map, centerName);

        map.add(spaceObjectName, center);
    }

    private static SpaceObject getOrCreateCenter(SpaceMap map, String centerName) {
        var center = map.get(centerName);
        if (center == null) {
            map.add(centerName, SpaceObject.COM());
        }
        center = map.get(centerName);
        return center;
    }

    private static void validateNotation(String mapNotation) {
        if (!mapNotation.matches("[A-Z0-9]{3}\\)[A-Z0-9]{3}"))
            throw new IllegalArgumentException("Invalid map notation " + mapNotation);
    }
}
