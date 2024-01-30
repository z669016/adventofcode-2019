package com.putoet.day25;

import com.putoet.resources.CSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class Game {
    public static final Set<String> AVOID = Set.of("infinite loop");

    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day25.txt", Long::parseLong);

        final var droid = new Droid(intCode);
        final var reader = new BufferedReader(new InputStreamReader(System.in));

        var command = "";
        droid.start();
        var output = response(droid);
        output.forEach(System.out::println);

        while (!"exit".equals(command)) {
            System.out.print("=> ");
            command = readCommand(reader);
            // System.out.println(command);

            if (!"exit".equals(command)) {
                if ("help".equals(command)) {
                    System.out.println("Available commands:");
                    System.out.println("north - move north");
                    System.out.println("south - move south");
                    System.out.println("east - move east");
                    System.out.println("west - move west");
                    System.out.println("inv - show an overview of the inventory");
                    System.out.println("take <item> - pick up the named item");
                    System.out.println("drop <item> - drop the named item");
                    System.out.println("exit - exit the game");
                    System.out.println();
                } else {
                    Arrays.stream(command.split(",")).map(String::trim).forEach(droid::offer);
                }
            }
            output = response(droid);
            output.forEach(System.out::println);
        }
        droid.stop();
    }

    static List<String> response(Droid droid) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        return droid.poll();
    }

    static String readCommand(BufferedReader reader) {
        try {
            return reader.readLine().toLowerCase();
        } catch (IOException ignored) {
        }

        return "";
    }

    static List<String> play(List<Long> intCode, List<String> commands) {
        final var droid = new Droid(intCode);

        droid.start();
        for (var command : commands) {
            droid.offer(command);
        }
        final var output = response(droid);
        droid.stop();

        return output;
    }
}
