package com.putoet.day12;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MoonTest {
    private Map<String,Moon> moons = new HashMap<>();

    @Before
    public void setup() {
        moons.put("Io", Moon.of("Io", "<x=-1, y=0, z=2>"));
        moons.put("Europa", Moon.of("Europa", "<x=2, y=-10, z=-7>"));
        moons.put("Ganymede", Moon.of("Ganymede", "<x=4, y=-8, z=8>"));
        moons.put("Calisto", Moon.of("Calisto", "<x=3, y=5, z=-1>"));
    }

    @Test
    public void testOf() {
        assertEquals(new Moon("Moon", new Position(1, 2, 3)), Moon.of("Moon", "<x=1, y=2, z=3>"));
    }

    @Test
    public void testApplyGravity() {
        moons.values().forEach(moon -> moon.applyGravity(moons));

        assertEquals("<x=3, y=-1, z=-1>", moons.get("Io").velocity().toString());
        assertEquals("<x=1, y=3, z=3>", moons.get("Europa").velocity().toString());
        assertEquals("<x=-3, y=1, z=-3>", moons.get("Ganymede").velocity().toString());
        assertEquals("<x=-1, y=-3, z=1>", moons.get("Calisto").velocity().toString());
    }

    @Test
    public void testApplyVelocity() {
        moons.values().forEach(moon -> moon.applyGravity(moons));
        moons.values().forEach(Moon::applyVelocity);

        assertEquals("<x=2, y=-1, z=1>", moons.get("Io").position().toString());
        assertEquals("<x=3, y=-7, z=-4>", moons.get("Europa").position().toString());
        assertEquals("<x=1, y=-7, z=5>", moons.get("Ganymede").position().toString());
        assertEquals("<x=2, y=2, z=0>", moons.get("Calisto").position().toString());
    }

    @Test
    public void testEnergyTest() {
        for (int idx = 0; idx < 10; idx++) {
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);

            moons.values().forEach(System.out::println);
            System.out.println("Total energy in the  system is: " + moons.values().stream().mapToInt(Moon::totalEnergy).sum());

            System.out.println();
        }

        assertEquals(36, moons.get("Io").totalEnergy());
        assertEquals(45, moons.get("Europa").totalEnergy());
        assertEquals(80, moons.get("Ganymede").totalEnergy());
        assertEquals(18, moons.get("Calisto").totalEnergy());

//        moons.values().forEach(moon -> System.out.println(moon));
    }
}