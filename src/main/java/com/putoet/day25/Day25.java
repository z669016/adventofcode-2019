package com.putoet.day25;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.*;


public class Day25 {

    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day25.txt", Long::parseLong);
        Timer.run(() -> System.out.println("Passcode: " + findAccessCode(intCode).orElse("not found")));
    }

    private static Optional<String> findAccessCode(List<Long> intCode) {
        final Set<Set<String>> visitedItems = new HashSet<>();
        final Queue<State> queue = new PriorityQueue<>();
        Optional<String> finalDirection = Optional.empty();
        int attempts = 0;

        queue.offer(new State("START"));
        Optional<String> passcode = Optional.empty();
        while (!queue.isEmpty()) {
            final var current = queue.poll();
            if (current.room().equals(State.CHECKPOINT) && !current.command().startsWith(State.DROP)) {
                System.out.println("Attempt " + ++attempts + " with " + current.items() + " ");
            }

            final var response = Game.play(intCode, current.commands());
            if (response.isEmpty()) {
                System.out.println("No response");
                continue;
            }

            passcode = passCode(response);
            if (passcode.isPresent()) {
                break;
            }

            final var location = Location.of(response);
            if (location.room().equals(State.CHECKPOINT)) {
                if (finalDirection.isEmpty()) {
                    finalDirection = Optional.of(current.command());
                }

                if (!current.room().equals(State.CHECKPOINT) || current.command().startsWith(State.DROP)) {
                    final var next = current.enter(location.room(), finalDirection.get());
                    if (visitedItems.add(next.items()))
                        queue.offer(next);
                } else {
                    current.items().forEach(item -> current.drop(location.room(), item).ifPresent(queue::offer));
                }
            } else {
                location.items().forEach(item -> {
                    if (!Game.AVOID.contains(item)) {
                        final var next = current.take(location.room(), item);
                        next.ifPresent(queue::offer);
                    }
                });

                if (location.items().isEmpty()) {
                    location.doors().forEach(door -> {
                        final var next = current.move(location.room(), door);
                        next.ifPresent(queue::offer);
                    });
                }
            }
        }
        return passcode;
    }

    private static Optional<String> passCode(List<String> output) {
        return output.stream()
                .filter(line -> line.contains(" typing "))
                .findFirst();
    }
}
