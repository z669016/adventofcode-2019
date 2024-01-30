package com.putoet.day25;

import java.util.*;

record Location(String room, List<String> description, Set<String> items, Set<String> doors, List<String> story) {
    static Location of(List<String> output) {
        final var lastRoom = lastRoom(output);
        if (lastRoom.isEmpty()) {
            System.err.println("No room found");
            output.forEach(System.err::println);
            System.exit(1);
        }

        return of(output, lastRoom.getAsInt(), output.size());
    }

    private static Location of(List<String> output, int start, int end) {
        String name = "";
        List<String> description = Collections.emptyList();
        Set<String> items = Collections.emptySet();
        Set<String> doors = Collections.emptySet();
        List<String> story = new ArrayList<>();

        for (int idx = start; idx < end; idx++) {
            final var line = output.get(idx);
            if (line.isBlank())
                continue;

            if (line.startsWith("==")) {
                name = line.substring(3, line.length() - 3);
                description = new ArrayList<>();
                idx++;
                while (idx < output.size() && !output.get(idx).isBlank()) {
                    description.add(output.get(idx));
                    idx++;
                }
                continue;
            }

            if (line.startsWith("Doors here lead:")) {
                doors = set(output, idx + 1);
                idx += doors.size() + 1;
                continue;
            }

            if (line.startsWith("Items here:")) {
                items = set(output, idx + 1);
                idx += items.size() + 1;
                continue;
            }

            if (line.startsWith("You take the ")) {
                items = new HashSet<>(items);
                items.remove(line.substring(13, line.length() - 1));
                continue;
            }

            story.add(line);
        }

        if (name.equals("Security Checkpoint")) {
            final var beforeLastRoom = beforeLastRoom(output).orElseThrow();
            final var check = of(output, beforeLastRoom, start);
            if (check.room.equals("Pressure-Sensitive Floor")) {
                story.addAll(0, check.toString().lines().toList());
            }
        }

        return new Location(name, description, items, doors, story);
    }

    private static Set<String> set(List<String> output, int idx) {
        final var set = new HashSet<String>();
        while (idx < output.size() && !output.get(idx).isBlank()) {
            set.add(output.get(idx).substring(2));
            idx++;
        }
        return set;
    }

    private static OptionalInt lastRoom(List<String> output) {
        var lastRoom = OptionalInt.empty();

        for (int idx = 0; idx < output.size(); idx++) {
            if (output.get(idx).startsWith("==")) {
                lastRoom = OptionalInt.of(idx);
            }
        }

        return lastRoom;
    }

    private static OptionalInt beforeLastRoom(List<String> output) {
        final var lastRoom = lastRoom(output).orElseThrow();
        var beforeLastRoom = OptionalInt.empty();

        for (int idx = 0; idx < lastRoom; idx++) {
            if (output.get(idx).startsWith("==")) {
                beforeLastRoom = OptionalInt.of(idx);
            }
        }

        return beforeLastRoom;
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        sb.append("== ").append(room).append(" ==\n");
        description.forEach(line -> sb.append(line).append("\n"));
        sb.append("\n");
        sb.append("Doors here lead:\n");
        doors.forEach(line -> sb.append("- ").append(line).append("\n"));
        sb.append("\n");
        sb.append("Items here:\n");
        items.forEach(line -> sb.append("- ").append(line).append("\n"));
        sb.append("\n");
        story.forEach(line -> sb.append(line).append("\n"));
        return sb.toString();
    }
}
