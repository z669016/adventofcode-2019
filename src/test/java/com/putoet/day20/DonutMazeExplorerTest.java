package com.putoet.day20;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonutMazeExplorerTest {
    private DonutMazeExplorer explorer1;
    private DonutMazeExplorer explorer2;

    @BeforeEach
    void setup() {
        var maze = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-1.txt"), ' ')));
        explorer1 = new DonutMazeExplorer(maze);

        maze = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-2.txt"), ' ')));
        explorer2 = new DonutMazeExplorer(maze);
    }

    @Test
    void shortestRoute() {
        assertEquals(23, explorer1.shortestRoute());
        assertEquals(58, explorer2.shortestRoute());
    }

    @Test
    void init() {
        assertEquals(Point.of(9, 2), explorer1.init());
        assertEquals(Point.of(19, 2), explorer2.init());
    }

    @Test
    void success() {
        assertTrue(explorer1.success(Point.of(13, 16)));
        assertTrue(explorer2.success(Point.of(2, 17)));

    }

    @Test
    void successors() {
        assertEquals(List.of(Point.of(9, 3)), explorer1.successors(explorer1.init()));
        assertEquals(List.of(Point.of(19, 3)), explorer2.successors(explorer2.init()));

        assertEquals(List.of(Point.of(2, 8), Point.of(9, 5)), explorer1.successors(Point.of(9, 6)));
        assertEquals(List.of(Point.of(15, 28), Point.of(31, 21)), explorer2.successors(Point.of(32, 21)));
    }
}