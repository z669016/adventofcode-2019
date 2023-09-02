package com.putoet.day6;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpaceMapLoaderTest {
    @Test
    public void loadMap() {
        final var mapNotations = List.of("COM)BBB",
                "BBB)CCC", "CCC)DDD", "DDD)EEE", "EEE)FFF", "BBB)GGG",
                "GGG)HHH", "DDD)III", "EEE)JJJ", "JJJ)KKK", "KKK)LLL");

        final var map = SpaceMapLoader.loadMap(mapNotations);
        assertEquals(12, map.objects().size());
        assertEquals(42, map.orbits());
    }
}