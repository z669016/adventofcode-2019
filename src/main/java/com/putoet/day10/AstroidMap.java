package com.putoet.day10;

import java.util.*;

public class AstroidMap {
    private final Set<Astroid> astroidMap = new HashSet<>();

    public static AstroidMap of(List<String> mapLines) {
        if (mapLines == null)
            throw new IllegalArgumentException("No map");

        if (mapLines.stream().noneMatch(s -> s != null && s.length() > 0))
            throw new IllegalArgumentException("Empty map");

        final AstroidMap astroidMap = new AstroidMap();
        for (int idy = 0; idy < mapLines.size(); idy++) {
            final String mapLine = mapLines.get(idy);
            if (mapLine != null) {
                for (int idx = 0; idx < mapLine.length(); idx++) {
                    if (mapLine.charAt(idx) == '#') {
                        astroidMap.add(new Astroid(new Point(idx, idy)));
                    }
                }
            }
        }

        return astroidMap;
    }

    private void add (Astroid astroid) {
        astroidMap.add(astroid);
    }

    public Set<Astroid> astroids() {
        return Collections.unmodifiableSet(astroidMap);
    }

    public Map<Astroid, LineOfSightMap> linesOfSightMaps() {
        final Map<Astroid, LineOfSightMap> map = new HashMap<>();
        astroidMap.forEach(astroid -> map.put(astroid, LineOfSightMap.of(astroid, astroidMap)));
        return Collections.unmodifiableMap(map);
    }

    public Optional<Astroid> astroidAt(Point point) {
        return astroidMap.stream().filter(astroid -> astroid.location().equals(point)).findFirst();
    }

    public Optional<LineOfSightMap> linesOfSightMapFor(Astroid astroid) {
        return Optional.ofNullable(astroidMap.contains(astroid) ? LineOfSightMap.of(astroid, astroidMap) : null);
    }
}
