package com.putoet.day6;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SpaceMapLoaderTest {
    @Test
    public void loadMap() {
        final List<String> mapNotations = List.of("COM)BBB",
                "BBB)CCC", "CCC)DDD", "DDD)EEE", "EEE)FFF", "BBB)GGG",
                "GGG)HHH", "DDD)III", "EEE)JJJ", "JJJ)KKK", "KKK)LLL");

        SpaceMapLoader.loadMap(mapNotations);

        assertEquals(12, SpaceMap.MAP.objects().size());
        assertEquals(42, SpaceMap.MAP.orbits());
        System.out.println(SpaceMap.MAP.objects());
    }
}