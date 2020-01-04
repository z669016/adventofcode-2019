package com.putoet.day12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day12b {
    public static void main(String[] args) {
        final Map<String,Moon> m = MoonMap.loadFile("day12.txt");
        final Moon io = m.get("Io");
        final Moon europa = m.get("Europa");
        final Moon ganymede = m.get("Ganymede");
        final Moon calisto = m.get("Calisto");

        final Map<String,Moon> moons = MoonMap.loadFile("day12.txt");

//        final Moon io = Moon.of("Io", "<x=-1, y=0, z=2>");
//        final Moon europa = Moon.of("Europa", "<x=2, y=-10, z=-7>");
//        final Moon ganymede =Moon.of("Ganymede", "<x=4, y=-8, z=8>");
//        final Moon calisto = Moon.of("Calisto", "<x=3, y=5, z=-1>");
//
//        final Map<String,Moon> moons = new HashMap<>();
//        moons.put("Io", Moon.of("Io", "<x=-1, y=0, z=2>"));
//        moons.put("Europa", Moon.of("Europa", "<x=2, y=-10, z=-7>"));
//        moons.put("Ganymede", Moon.of("Ganymede", "<x=4, y=-8, z=8>"));
//        moons.put("Calisto", Moon.of("Calisto", "<x=3, y=5, z=-1>"));

        //final Set<String> snapshots = new HashSet<>();
        long idx = 0;
        do {
            System.out.print(++idx);
            System.out.print("\r");
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);
            //} while (snapshots.add(MoonMap.snapshot(moons)));
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
