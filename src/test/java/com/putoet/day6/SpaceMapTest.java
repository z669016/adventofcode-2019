package com.putoet.day6;

import org.junit.Before;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.*;

public class SpaceMapTest {
    public static final String AAA_NAME = "AAA";
    public static final String BBB_NAME = "BBB";
    public static final String CCC_NAME = "CCC";
    public static final String DDD_NAME = "DDD";
    public static final String EEE_NAME = "EEE";

    @Before
    public void setup() {
        SpaceMap.MAP.add(AAA_NAME, SpaceObject.COM());
        SpaceMap.MAP.add(BBB_NAME, SpaceMap.MAP.get(AAA_NAME));
        SpaceMap.MAP.add(CCC_NAME, SpaceMap.MAP.get(BBB_NAME));
        SpaceMap.MAP.add(DDD_NAME, SpaceMap.MAP.get(BBB_NAME));
        SpaceMap.MAP.add(EEE_NAME, SpaceMap.MAP.get(DDD_NAME));
    }

    @Test
    public void add() {
        assertEquals(6, SpaceMap.MAP.objects().size());
        assertTrue(SpaceMap.MAP.objects().containsKey(AAA_NAME));
        assertTrue(SpaceMap.MAP.objects().containsKey(BBB_NAME));
        assertTrue(SpaceMap.MAP.objects().containsKey(CCC_NAME));
        assertTrue(SpaceMap.MAP.objects().containsKey(DDD_NAME));
        assertTrue(SpaceMap.MAP.objects().containsKey(EEE_NAME));
    }

    @Test
    public void get() {
        assertEquals(SpaceObject.COM(), SpaceMap.MAP.get("COM"));
        SpaceObject so = SpaceMap.MAP.get(AAA_NAME);
        assertEquals(AAA_NAME, so.name());
        assertEquals(SpaceObject.COM(), so.center());

        so = SpaceMap.MAP.get(BBB_NAME);
        assertEquals(BBB_NAME, so.name());
        assertEquals(SpaceMap.MAP.get(AAA_NAME), so.center());

        so = SpaceMap.MAP.get(CCC_NAME);
        assertEquals(CCC_NAME, so.name());
        assertEquals(SpaceMap.MAP.get(BBB_NAME), so.center());
    }

    @Test
    public void testDistance() {
        final SpaceObject AAA = new SpaceObject(AAA_NAME, SpaceObject.COM());
        final SpaceObject BBB = new SpaceObject(BBB_NAME, AAA);
        final SpaceObject CCC = new SpaceObject(CCC_NAME, BBB);
        final SpaceObject DDD = new SpaceObject(DDD_NAME, BBB);
        final SpaceObject EEE = new SpaceObject(EEE_NAME, DDD);

        assertEquals(1, SpaceMap.MAP.distance(EEE, CCC));
        assertEquals(2, SpaceMap.MAP.distance(EEE, AAA));
        assertEquals(0, SpaceMap.MAP.distance(DDD, CCC));
    }
}