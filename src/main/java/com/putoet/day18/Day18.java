package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day18 {
    public static void main(String[] args) {
        final var keyMap = KeyMap.of(ResourceLines.list("/day18.txt"));
        System.out.println(keyMap);

        Timer.run(() ->
                System.out.println("Minimum number of steps to collect all keys is " + Explorer.collectAllKeys(keyMap))
        );

        Timer.run(() ->
                System.out.println("Minimum number of steps to collect all keys is on the split key map is " +
                                   MultiMapExplorer.collectAllKeys(keyMap.split().keyMaps()))
        );
    }
}
