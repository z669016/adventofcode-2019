package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErisTest {
    private Eris eris;

    @BeforeEach
    void setup() {
        eris = new Eris(new Grid(GridUtils.of(ResourceLines.list("/day24.txt"))));
    }

    @Test
    void biodiversityRating() {
        final var map = List.of(
                ".....",
                ".....",
                ".....",
                "#....",
                ".#..."
        );
        final var eris = new Eris(new Grid(GridUtils.of(map)));

        assertEquals(2_129_920L, eris.biodiversityRating());
    }

    @Test
    void evolve() {
        final var one = List.of("#..#.","####.", "###.#", "##.##", ".##..");
        final var two = List.of("#####", "....#", "....#", "...#.", "#.###");
        final var three = List.of("#....", "####.", "...##", "#.##.", ".##.#");
        final var four = List.of("####.", "....#", "##..#", ".....", "##...");

        var next = eris.evolve();
        assertTrue(Arrays.deepEquals(next.grid().grid(), GridUtils.of(one)));
        next = next.evolve();
        assertTrue(Arrays.deepEquals(next.grid().grid(), GridUtils.of(two)));
        next = next.evolve();
        assertTrue(Arrays.deepEquals(next.grid().grid(), GridUtils.of(three)));
        next = next.evolve();
        assertTrue(Arrays.deepEquals(next.grid().grid(), GridUtils.of(four)));
    }
}