package com.putoet.day12;

import java.util.Map;

public class Day12b {
    public static void main(String[] args) {
        final Map<String, Moon> m = MoonMap.loadFile("day12.txt");
        final Moon io = m.get("Io");
        final Moon europa = m.get("Europa");
        final Moon ganymede = m.get("Ganymede");
        final Moon calisto = m.get("Calisto");

        final Map<String, Moon> moons = MoonMap.loadFile("day12.txt");

        long idx = 0;
        do {
            System.out.print(++idx);
            System.out.print("\r");
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);
        } while (!(io.position().equals(moons.get("Io").position())
                && io.velocity().equals(moons.get("Io").velocity())
                && europa.position().equals(moons.get("Europa").position())
                && europa.velocity().equals(moons.get("Europa").velocity())
                && ganymede.position().equals(moons.get("Ganymede").position())
                && ganymede.velocity().equals(moons.get("Ganymede").velocity())
                && calisto.position().equals(moons.get("Calisto").position())
                && calisto.velocity().equals(moons.get("Calisto").velocity()))
        );

        System.out.println();
        System.out.println("History repeated after " + idx + " steps");
    }
}
