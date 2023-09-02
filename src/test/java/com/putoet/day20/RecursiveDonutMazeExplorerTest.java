package com.putoet.day20;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveDonutMazeExplorerTest {
    private RecursiveDonutMazeExplorer explorer1;
    private RecursiveDonutMazeExplorer explorer2;
    private RecursiveDonutMazeExplorer explorer3;

    @BeforeEach
    void setup() {
        var maze = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-1.txt"), ' ')));
        explorer1 = new RecursiveDonutMazeExplorer(maze);

        maze = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-2.txt"), ' ')));
        explorer2 = new RecursiveDonutMazeExplorer(maze);

        maze = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-3.txt"), ' ')));
        explorer3 = new RecursiveDonutMazeExplorer(maze);
    }

    @Test
    void shortestRoute() {
        assertEquals(396, explorer3.shortestRoute());
    }

    @Test
    void init() {
        assertEquals(Pair.with(0, Point.of(9, 2)), explorer1.init());
        assertEquals(Pair.with(0, Point.of(19, 2)), explorer2.init());
    }

    @Test
    void success() {
        assertTrue(explorer1.success(Pair.with(0, Point.of(13, 16))));
        assertTrue(explorer2.success(Pair.with(0, Point.of(2, 17))));

        assertFalse(explorer1.success(Pair.with(-1, Point.of(13, 16))));
        assertFalse(explorer2.success(Pair.with(-1, Point.of(2, 17))));
    }

    @Test
    void successors() {
        assertEquals(List.of(Pair.with(0, Point.of(9, 3))), explorer1.successors(explorer1.init()));
        assertEquals(List.of(Pair.with(0, Point.of(19, 3))), explorer2.successors(explorer2.init()));

        assertEquals(List.of(Pair.with(-1, Point.of(2, 8)), Pair.with(0, Point.of(9, 5))), explorer1.successors(Pair.with(0, Point.of(9, 6))));
        assertEquals(List.of(Pair.with(-1, Point.of(3, 8)), Pair.with(0, Point.of(9, 6))), explorer1.successors(Pair.with(-1, Point.of(2, 8))));
    }
}