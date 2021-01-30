package com.putoet.day18;

import com.putoet.grid.Point;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static com.putoet.search.GenericSearch.Node;

public class Explorer {
    private final KeyMap keyMap;
    private final Set<Character> allKeys;

    private final PriorityQueue<State> queue;
    private final Map<String,Integer> shortest = new HashMap<>();


    private Explorer(KeyMap keyMap) {
        this.keyMap = keyMap;
        this.allKeys = keyMap.keys().stream().map(Pair::getValue0).collect(Collectors.toSet());
        this.queue = new PriorityQueue<>(Comparator.comparingInt(state -> state.steps));
    }

    public static int collectAllKeys(KeyMap keyMap) {
        final Explorer explorer = new Explorer(keyMap);
        return explorer.collectAllKeys();
    }

    private int collectAllKeys() {
        queue.clear();
        shortest.clear();

        final State initialState = new State(keyMap.entrance(), Set.of(), 0);
        queue.offer(initialState);

        State current = queue.poll();
        while (!foundAll(current)) {
            addAvailableKeys(queue, current);
            current = queue.poll();
        }

        return current.steps;
    }

    private void addAvailableKeys(PriorityQueue<State> queue, final State current) {
        final List<Node<Point>> availableKeys = keyMap.availableKeys(current.location, current.keys);
        availableKeys.forEach(node -> {
            final Point nextLocation = node.state;
            final Set<Character> nextKeys = new HashSet<>(current.keys);
            nextKeys.add(keyMap.elementAt(node.state));
            final int nextSteps = current.steps + node.steps();

            final String mapKey = nextLocation.toString() + nextKeys.toString();
            if (!shortest.containsKey(mapKey) || shortest.get(mapKey) > nextSteps) {
                final State next = new State(nextLocation, nextKeys, nextSteps);
                shortest.put(mapKey, nextSteps);
                queue.offer(next);
            }
        });
    }

    public boolean foundAll(State state) {
        return allKeys.equals(state.keys);
    }

    public static class State {
        public final Set<Character> keys;
        public final Point location;
        public final int steps;

        public State(Point location, Set<Character> keys, int steps) {
            this.keys = keys;
            this.location = location;
            this.steps = steps;
        }
    }
}
