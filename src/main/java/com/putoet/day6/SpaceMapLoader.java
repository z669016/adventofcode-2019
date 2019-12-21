package com.putoet.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceMapLoader {
    private static final List<String> comCenteredSpaceObjects = new ArrayList<>();

    public static void loadMap(String fileName) {
        try {
            Files.lines(Paths.get(fileName)).forEach(line -> createSpaceObjects(line));
        } catch (IOException exc) {
            throw new IllegalArgumentException("Unable to load map from " + fileName, exc);
        }

        checkUncenteredSpaceObjects();
    }

    public static void loadMap(List<String> mapNotations) {
        mapNotations.forEach(mapNotation -> createSpaceObjects(mapNotation));

        checkUncenteredSpaceObjects();
    }

    private static void createSpaceObjects(String mapNotation) {
        validateNotation(mapNotation);

        final String centerName = mapNotation.substring(0, 3);
        final String spaceObjectName = mapNotation.substring(4);

        registerComCenteredSpaceObjects(centerName, spaceObjectName);

        final SpaceObject center = getOrCreateCenter(centerName);
        SpaceMap.MAP.add(spaceObjectName, center);
    }

    private static void checkUncenteredSpaceObjects() {
        final List<String> unCenteredSpaceObjects = SpaceMap.MAP.objects().entrySet().stream()
                .filter(entry -> entry.getValue() != SpaceObject.COM())
                .filter(entry -> entry.getValue().center() == SpaceObject.COM())
                .filter(entry -> !comCenteredSpaceObjects.contains(entry.getKey()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        if (unCenteredSpaceObjects.size() > 0) {
            System.out.println("Uncentered space objects are " + unCenteredSpaceObjects.toString());
        }
    }

    private static SpaceObject getOrCreateCenter(String centerName) {
        SpaceObject center = SpaceMap.MAP.get(centerName);
        if (center == null)
            SpaceMap.MAP.add(centerName, SpaceObject.COM());
        center = SpaceMap.MAP.get(centerName);
        return center;
    }

    private static void registerComCenteredSpaceObjects(String centerName, String spaceObjectName) {
        if ("COM".equals(centerName))
            comCenteredSpaceObjects.add(spaceObjectName);
    }

    private static void validateNotation(String mapNotation) {
        if (!mapNotation.matches("[A-Z0-9]{3}\\)[A-Z0-9]{3}"))
            throw new IllegalArgumentException("Invalid map notation " + mapNotation);
    }

}
