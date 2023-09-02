package com.putoet.day18;

import com.putoet.grid.Point;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MultiMapExplorer {
    private final KeyMap[] keyMaps;
    private final Set<Character> allKeys;

    private final PriorityQueue<State> queue;
    private final Map<String, Integer> shortest = new HashMap<>();

    private MultiMapExplorer(KeyMap[] keyMaps) {
        assert keyMaps != null && keyMaps.length > 0;

        this.keyMaps = keyMaps;
        this.allKeys = Arrays.stream(keyMaps)
                .flatMap(keyMap -> keyMap.keys().stream())
                .map(Pair::getValue0)
                .collect(Collectors.toSet());
        this.queue = new PriorityQueue<>(Comparator.comparingInt(state -> state.steps));
    }

    public static int collectAllKeys(@NotNull KeyMap[] keyMaps) {
        final MultiMapExplorer explorer = new MultiMapExplorer(keyMaps);
        return explorer.collectAllKeys();
    }

    private int collectAllKeys() {
        queue.clear();
        shortest.clear();

        final var initialState =
                new State(Arrays.stream(keyMaps).map(KeyMap::entrance).collect(Collectors.toList()));
        queue.offer(initialState);

        var current = queue.poll();
        while (!foundAll(Objects.requireNonNull(current))) {
            addAvailableKeys(queue, current);
            current = queue.poll();
        }

        return current.steps;
    }

    private void addAvailableKeys(PriorityQueue<State> queue, final State current) {
        final var availableKeys = IntStream.range(0, keyMaps.length)
                        .mapToObj(i -> new Pair<>(i, keyMaps[i].availableKeys(current.locations.get(i), current.keys)))
                        .toList();

        availableKeys.forEach(pair -> {
            final var id = pair.getValue0();

            pair.getValue1().forEach(node -> {
                final var nextLocations = new ArrayList<>(current.locations);
                nextLocations.set(id, node.state);

                final var nextKeys = new HashSet<>(current.keys);
                nextKeys.add(elementAt(node.state));

                final var nextSteps = current.steps + node.steps();

                final var mapKey = nextLocations + nextKeys.toString();
                if (!shortest.containsKey(mapKey) || shortest.get(mapKey) > nextSteps) {
                    final var next = new State(nextLocations, nextKeys, nextSteps);
                    shortest.put(mapKey, nextSteps);
                    queue.offer(next);
                }
            });
        });
    }

    private Character elementAt(Point point) {
        for (var keyMap : keyMaps) {
            if (keyMap.contains(point))
                return keyMap.elementAt(point);
        }

        throw new IllegalStateException("Point " + point + " not on any of the key maps");
    }

    public boolean foundAll(@NotNull State state) {
        return allKeys.equals(state.keys);
    }

    public record State(@NotNull List<Point> locations, @NotNull Set<Character> keys, int steps) {
        public State(@NotNull List<Point> locations) {
            this(locations, Set.of(), 0);
        }
    }
}
