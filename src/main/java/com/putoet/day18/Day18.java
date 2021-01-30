package com.putoet.day18;

import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day18 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day18.txt");

        final KeyMap keyMap = KeyMap.of(lines);
        System.out.println(keyMap);

        part1(keyMap);
        part2(keyMap);
    }

    private static void part1(KeyMap keyMap) {
        final long start = System.currentTimeMillis();
        final int steps = Explorer.collectAllKeys(keyMap);
        final long end =System.currentTimeMillis();

        System.out.println("Minimum number of steps to collect all keys is " + steps);
        System.out.println("Solving this part took " + (end - start) +" ms.");
    }

    private static void part2(KeyMap keyMap) {
        final long start = System.currentTimeMillis();
        final int steps = MultiMapExplorer.collectAllKeys(keyMap.split().keyMaps());
        final long end =System.currentTimeMillis();

        System.out.println("Minimum number of steps to collect all keys is on the split key map is " + steps);
        System.out.println("Solving this part took " + (end - start) +" ms.");
    }
}
