package com.putoet.day18;

import com.putoet.grid.Point;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

class Explorer {
    private final KeyMap keyMap;
    private final Set<Character> allKeys;

    private final PriorityQueue<State> queue;
    private final Map<String,Integer> shortest = new HashMap<>();

    record State(@NotNull Point location, @NotNull Set<Character> keys, int steps) {
        public State(@NotNull Point location) {
            this(location, new HashSet<>(), 0);
        }
    }

    private Explorer(KeyMap keyMap) {
        this.keyMap = keyMap;
        this.allKeys = keyMap.keys().stream().map(Pair::getValue0).collect(Collectors.toSet());
        this.queue = new PriorityQueue<>(Comparator.comparingInt(State::steps));
    }

    public static int collectAllKeys(@NotNull KeyMap keyMap) {
        final var explorer = new Explorer(keyMap);
        return explorer.collectAllKeys();
    }

    private int collectAllKeys() {
        queue.clear();
        shortest.clear();

        final var initialState = new State(keyMap.entrance());
        queue.offer(initialState);

        var current = queue.poll();
        while (!foundAll(Objects.requireNonNull(current))) {
            addAvailableKeys(queue, current);
            current = queue.poll();
        }

        return current.steps();
    }

    private void addAvailableKeys(PriorityQueue<State> queue, final State current) {
        final var availableKeys = keyMap.availableKeys(current.location(), current.keys());
        availableKeys.forEach(node -> {
            final var nextLocation = node.state;
            final var nextKeys = new HashSet<>(current.keys());
            final int nextSteps = current.steps() + node.steps();
            final var mapKey = nextLocation + nextKeys.toString();

            nextKeys.add(keyMap.elementAt(node.state));

            if (!shortest.containsKey(mapKey) || shortest.get(mapKey) > nextSteps) {
                final var next = new State(nextLocation, nextKeys, nextSteps);
                shortest.put(mapKey, nextSteps);
                queue.offer(next);
            }
        });
    }

    public boolean foundAll(@NotNull Explorer.State state) {
        return allKeys.equals(state.keys());
    }
}
