package com.putoet.day25;

import com.putoet.resources.CSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Day25 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day25.txt", Long::parseLong);
        final Droid droid = new Droid(intCode);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String command = "";
        droid.start();
        showDroidResponse(droid);

        while (command != null && !"exit".equals(command)) {
            System.out.print("=> ");
            command = readCommand(reader);
            System.out.println(command);

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
                    System.out.println();
                } else {
                    droid.offer(command);
                }
            }
            showDroidResponse(droid);
        }
        droid.stop();
    }

    public static void showDroidResponse(Droid droid) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        droid.poll().forEach(System.out::println);
    }

    private static String readCommand(BufferedReader reader) {
        try {
            return reader.readLine().toLowerCase();
        } catch (IOException ignored) {
        }

        return "";
    }
}
