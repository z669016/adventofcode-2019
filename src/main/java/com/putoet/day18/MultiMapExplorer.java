package com.putoet.day18;

import com.putoet.grid.Point;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.putoet.search.GenericSearch.Node;

public class MultiMapExplorer {
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

    public static int collectAllKeys(KeyMap[] keyMaps) {
        final MultiMapExplorer explorer = new MultiMapExplorer(keyMaps);
        return explorer.collectAllKeys();
    }

    private int collectAllKeys() {
        queue.clear();
        shortest.clear();

        final State initialState =
                new State(Arrays.stream(keyMaps).map(KeyMap::entrance).collect(Collectors.toList()));
        queue.offer(initialState);

        State current = queue.poll();
        while (current != null && !foundAll(current)) {
            addAvailableKeys(queue, current);
            current = queue.poll();
        }

        return current.steps;
    }

    private void addAvailableKeys(PriorityQueue<State> queue, final State current) {
        final List<Pair<Integer, List<Node<Point>>>> availableKeys =
                IntStream.range(0, keyMaps.length)
                        .mapToObj(i -> new Pair<>(i, keyMaps[i].availableKeys(current.locations.get(i), current.keys)))
                        .collect(Collectors.toList());

        availableKeys.forEach(pair -> {
            final int id = pair.getValue0();

            pair.getValue1().forEach(node -> {
                final List<Point> nextLocations = new ArrayList<>(current.locations);
                nextLocations.set(id, node.state);

                final Set<Character> nextKeys = new HashSet<>(current.keys);
                nextKeys.add(elementAt(node.state));

                final int nextSteps = current.steps + node.steps();

                final String mapKey = nextLocations.toString() + nextKeys.toString();
                if (!shortest.containsKey(mapKey) || shortest.get(mapKey) > nextSteps) {
                    final State next = new State(nextLocations, nextKeys, nextSteps);
                    shortest.put(mapKey, nextSteps);
                    queue.offer(next);
                }
            });
        });
    }

    private Character elementAt(Point point) {
        for (KeyMap keyMap : keyMaps) {
            if (keyMap.contains(point))
                return keyMap.elementAt(point);
        }

        throw new IllegalStateException("Point " + point + " not on any of the key maps");
    }

    public boolean foundAll(State state) {
        return allKeys.equals(state.keys);
    }

    public static class State {
        public final List<Point> locations;
        public final Set<Character> keys;
        public final int steps;

        public State(List<Point> locations, Set<Character> keys, int steps) {
            this.locations = locations;
            this.keys = keys;
            this.steps = steps;
        }

        public State(List<Point> locations) {
            this.locations = locations;
            this.keys = Set.of();
            this.steps = 0;
        }
    }
}
