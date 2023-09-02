package com.putoet.day20;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day20 {
    public static void main(String[] args) {
        final var grid = new Grid(GridUtils.of(ResourceLines.list("/day20.txt"), ' '));
        final var maze = new DonutMaze(grid);

        Timer.run(() -> part1(maze));
        Timer.run(() -> part2(maze));
    }

    private static void part1(DonutMaze maze) {
        final var explorer = new DonutMazeExplorer(maze);
        System.out.println("Shortest route through the maze takes " + explorer.shortestRoute() + " steps.");
    }

    private static void part2(DonutMaze maze) {
        final var explorer = new RecursiveDonutMazeExplorer(maze);
        System.out.println("Shortest recursive route through the maze takes " + explorer.shortestRoute() + " steps.");
    }
}
