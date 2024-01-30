package com.putoet.day25;

import org.jetbrains.annotations.NotNull;

import java.util.*;

record State(String room, Set<String> items, String command, int length, State previous) implements Comparable<State> {
    public State(String room) {
        this(room, Collections.emptySet(), "", 1, null);
    }

    public static final String CHECKPOINT = "Security Checkpoint";
    public static final String TAKE = "take ";
    public static final String DROP = "drop ";

    Optional<State> take(String room, String item) {
        final var command = TAKE + item;
        if (performed(room, command))
            return Optional.empty();

        final var newItems = new HashSet<>(items);
        newItems.add(item);
        return Optional.of(new State(room, newItems, command, length + 1, this));
    }

    Optional<State> drop(String room, String item) {
        final var command = DROP + item;
        if (performed(room, command))
            return Optional.empty();

        final var newItems = new HashSet<>(items);
        newItems.remove(item);
        return Optional.of(new State(room, newItems, "drop " + item, length + 1, this));
    }

    Optional<State> move(String room, String command) {
        if (performed(room, command))
            return Optional.empty();

        return Optional.of(new State(room, items, command, length + 1, this));
    }

    State enter(String from, String command) {
        return new State(from, items, command, length + 1, this);
    }

    private boolean performed(String room, String command) {
        var current = this;
        while (current != null) {
            if (current.room.equals(room) && current.command.equals(command))
                return true;

            current = current.previous;
        }
        return false;
    }

    List<String> commands() {
        var current = this;
        final var commands = new ArrayList<String>();
        while (current != null) {
            if (!current.command.isBlank())
                commands.add(current.command);

            current = current.previous;
        }
        return commands.reversed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;
        return Objects.equals(room, state.room) && Objects.equals(command, state.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, items, command);
    }

    @Override
    public int compareTo(@NotNull State other) {
        if (room.equals(CHECKPOINT))
            return -1;
        if (other.room.equals(CHECKPOINT))
            return 1;

        return Integer.compare(length, other.length);
    }
}
