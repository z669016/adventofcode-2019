package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErisTest {
    private Eris eris;

    @BeforeEach
    void setup() {
        final Grid grid = new Grid(GridUtils.of(ResourceLines.list("/day24.txt")));
        eris = new Eris(grid);
    }

    @Test
    void biodiversityRating() {
        final List<String> map = List.of(
                ".....",
                ".....",
                ".....",
                "#....",
                ".#..."
        );
        final Grid grid = new Grid(GridUtils.of(map));
        final Eris eris = new Eris(grid);

        assertEquals(2_129_920L, eris.biodiversityRating());
    }

    @Test
    void evolve() {
        final List<String> one = List.of("#..#.","####.", "###.#", "##.##", ".##..");
        final List<String> two = List.of("#####", "....#", "....#", "...#.", "#.###");
        final List<String> three = List.of("#....", "####.", "...##", "#.##.", ".##.#");
        final List<String> four = List.of("####.", "....#", "##..#", ".....", "##...");

        Eris next = eris.evolve();
        assertTrue(next.equals(GridUtils.of(one)));
        next = next.evolve();
        assertTrue(next.equals(GridUtils.of(two)));
        next = next.evolve();
        assertTrue(next.equals(GridUtils.of(three)));
        next = next.evolve();
        assertTrue(next.equals(GridUtils.of(four)));
    }
}