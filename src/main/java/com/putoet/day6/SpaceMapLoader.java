package com.putoet.day6;

import java.util.List;

public class SpaceMapLoader {
    private static boolean logEnabled = false;

    public static void enableLog() {
        logEnabled = true;
    }

    public static void disableLog() {
        logEnabled = false;
    }

    public static SpaceMap loadMap(List<String> mapNotations) {
        assert mapNotations != null;

        final SpaceMap map = new SpaceMap();
        mapNotations.forEach(notation -> createSpaceObjects(map, notation));
        return map;
    }

    private static void createSpaceObjects(SpaceMap map, String mapNotation) {
        validateNotation(mapNotation);

        final String centerName = mapNotation.substring(0, 3);
        final String spaceObjectName = mapNotation.substring(4);

        final SpaceObject center = getOrCreateCenter(map, centerName);
        // log("Created " + centerName + ")" + spaceObjectName + " for " + mapNotation);
        map.add(spaceObjectName, center);
    }

    private static SpaceObject getOrCreateCenter(SpaceMap map, String centerName) {
        SpaceObject center = map.get(centerName);
        if (center == null) {
            // log("Create " + centerName + " temporarily orbiting around COM");
            map.add(centerName, SpaceObject.COM());
        }
        center = map.get(centerName);
        return center;
    }

    private static void validateNotation(String mapNotation) {
        assert mapNotation != null;

        if (!mapNotation.matches("[A-Z0-9]{3}\\)[A-Z0-9]{3}"))
            throw new IllegalArgumentException("Invalid map notation " + mapNotation);
    }

    private static void log(String line) {
        if (logEnabled)
            System.out.println(line);
    }
}
