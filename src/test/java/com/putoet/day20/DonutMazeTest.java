package com.putoet.day20;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DonutMazeTest {
    private DonutMaze maze1;
    private DonutMaze maze2;

    @BeforeEach
    void setup() {
        maze1 = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-1.txt"), ' ')));
        maze2 = new DonutMaze(new Grid(GridUtils.of(ResourceLines.list("/day20-2.txt"), ' ')));
    }

    @Test
    void entry() {
        assertEquals(Point.of(9, 2), maze1.entry());
        assertEquals(Point.of(19, 2), maze2.entry());
    }

    @Test
    void exit() {
        assertEquals(Point.of(13, 16), maze1.exit());
        assertEquals(Point.of(2, 17), maze2.exit());
    }

    @Test
    @Disabled
    void print() {
        System.out.println(maze1);
        System.out.println(maze2);
    }
}