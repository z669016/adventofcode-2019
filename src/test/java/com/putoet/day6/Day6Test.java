package com.putoet.day6;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
    @Test
    public void testDay6B() {
        final var mapNotations = List.of(
                "COM)BBB", "BBB)CCC", "CCC)DDD", "DDD)EEE", "EEE)FFF", "BBB)GGG", "GGG)HHH",
                "DDD)III", "EEE)JJJ", "JJJ)KKK", "KKK)LLL", "KKK)YOU", "III)SAN");

        final var map = SpaceMapLoader.loadMap(mapNotations);
        assertEquals(4, map.distance(map.get("YOU"), map.get("SAN")));
    }
}
