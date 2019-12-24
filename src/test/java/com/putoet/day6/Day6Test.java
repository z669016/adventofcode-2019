package com.putoet.day6;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day6Test {
    @Test
    public void testDay6B() {
        final List<String> mapNotations = List.of(
                "COM)BBB", "BBB)CCC", "CCC)DDD", "DDD)EEE", "EEE)FFF", "BBB)GGG", "GGG)HHH",
                "DDD)III", "EEE)JJJ", "JJJ)KKK", "KKK)LLL", "KKK)YOU", "III)SAN");

        SpaceMap map = SpaceMapLoader.loadMap(mapNotations);
        assertEquals(4, map.distance(map.get("YOU"), map.get("SAN")));
    }
}
