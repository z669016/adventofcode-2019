package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveFoldedErisTest {
    private RecursiveFoldedEris eris;

    @BeforeEach
    void setup() {
        final var grid = GridUtils.of(ResourceLines.list("/day24.txt"));
        final var map = new HashMap<Integer,Grid>();
        map.put(0, new Grid(-2, 3, -2, 3, grid));
        map.put(-1, RecursiveFoldedEris.EMPTY_GRID);
        map.put(1, RecursiveFoldedEris.EMPTY_GRID);
        eris = new RecursiveFoldedEris(map);
    }

    @Test
    void tile19() {
        final var point = Point3D.of(1, 1, -1);
        final var expected = List.of(
                Point3D.of(1, 2, -1),
                Point3D.of(2, 1, -1),
                Point3D.of(1, 0, -1),
                Point3D.of(0, 1, -1)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tileG() {
        final var point = Point3D.of(-1, -1, 0);
        final var expected = List.of(
                Point3D.of(-1, 0, 0),
                Point3D.of(0, -1, 0),
                Point3D.of(-1, -2, 0),
                Point3D.of(-2, -1, 0)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tileD() {
        final var point = Point3D.of(1, -2, 0);
        final var expected = List.of(
                Point3D.of(1, -1, 0),
                Point3D.of(2, -2, 0),
                Point3D.of(0, -1, -1),
                Point3D.of(0, -2, 0)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tileE() {
        final var point = Point3D.of(2, -2, 0);
        final var expected = List.of(
                Point3D.of(2, -1, 0),
                Point3D.of(1, 0, -1),
                Point3D.of(0, -1, -1),
                Point3D.of(1, -2, 0)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tile14() {
        final var point = Point3D.of(1, 0, -1);
        final var expected = List.of(
                Point3D.of(1, 1, -1),
                Point3D.of(2, 0, -1),
                Point3D.of(1, -1, -1),
                Point3D.of(2, -2, 0),
                Point3D.of(2, -1, 0),
                Point3D.of(2, 0, 0),
                Point3D.of(2, 1, 0),
                Point3D.of(2, 2, 0)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tileN() {
        final var point = Point3D.of(1, 0, 0);
        final var expected = List.of(
                Point3D.of(1, 1, 0),
                Point3D.of(2, 0, 0),
                Point3D.of(1, -1, 0),
                Point3D.of(2, -2, 1),
                Point3D.of(2, -1, 1),
                Point3D.of(2, 0, 1),
                Point3D.of(2, 1, 1),
                Point3D.of(2, 2, 1)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tileL() {
        final var point = Point3D.of(-1, 0, 0);
        final var expected = List.of(
                Point3D.of(-1, 1, 0),
                Point3D.of(-2, -2, 1),
                Point3D.of(-2, -1, 1),
                Point3D.of(-2, 0, 1),
                Point3D.of(-2, 1, 1),
                Point3D.of(-2, 2, 1),
                Point3D.of(-1, -1, 0),
                Point3D.of(-2, 0, 0)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void tileH() {
        final var point = Point3D.of(0, -1, 0);
        final var expected = List.of(
                Point3D.of(-2, -2, 1),
                Point3D.of(-1, -2, 1),
                Point3D.of(0, -2, 1),
                Point3D.of(1, -2, 1),
                Point3D.of(2, -2, 1),
                Point3D.of(1, -1, 0),
                Point3D.of(0, -2, 0),
                Point3D.of(-1, -1, 0)
        );
        assertEquals(expected, eris.adjacent(point));
    }

    @Test
    void evolve() {
        var next = eris;
        for (int i = 0; i < 10; i++)
            next = next.evolve();

        assertEquals(99, next.bugs());
    }
}