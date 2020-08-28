package com.putoet.day6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceMapTest {
    public static final String AAA_NAME = "111";
    public static final String BBB_NAME = "222";
    public static final String CCC_NAME = "333";
    public static final String DDD_NAME = "444";
    public static final String EEE_NAME = "555";

    private SpaceMap map;
    
    @BeforeEach
    public void setup() {
        map = new SpaceMap();
        map.add(AAA_NAME, SpaceObject.COM());
        map.add(BBB_NAME, map.get(AAA_NAME));
        map.add(CCC_NAME, map.get(BBB_NAME));
        map.add(DDD_NAME, map.get(BBB_NAME));
        map.add(EEE_NAME, map.get(DDD_NAME));
    }

    @Test
    public void add() {
        assertEquals(6, map.objects().size());
        assertTrue(map.objects().containsKey(AAA_NAME));
        assertTrue(map.objects().containsKey(BBB_NAME));
        assertTrue(map.objects().containsKey(CCC_NAME));
        assertTrue(map.objects().containsKey(DDD_NAME));
        assertTrue(map.objects().containsKey(EEE_NAME));
    }

    @Test
    public void get() {
        assertEquals(SpaceObject.COM(), map.get("COM"));
        SpaceObject so = map.get(AAA_NAME);
        assertEquals(AAA_NAME, so.name());
        assertEquals(SpaceObject.COM(), so.center());

        so = map.get(BBB_NAME);
        assertEquals(BBB_NAME, so.name());
        assertEquals(map.get(AAA_NAME), so.center());

        so = map.get(CCC_NAME);
        assertEquals(CCC_NAME, so.name());
        assertEquals(map.get(BBB_NAME), so.center());
    }

    @Test
    public void testDistance() {
        final SpaceObject AAA = new SpaceObject(AAA_NAME, SpaceObject.COM());
        final SpaceObject BBB = new SpaceObject(BBB_NAME, AAA);
        final SpaceObject CCC = new SpaceObject(CCC_NAME, BBB);
        final SpaceObject DDD = new SpaceObject(DDD_NAME, BBB);
        final SpaceObject EEE = new SpaceObject(EEE_NAME, DDD);

        assertEquals(1, map.distance(EEE, CCC));
        assertEquals(2, map.distance(EEE, AAA));
        assertEquals(0, map.distance(DDD, CCC));
    }
}