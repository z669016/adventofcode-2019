package com.putoet.day20;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.resources.ResourceLines;

public class Day20 {
    public static void main(String[] args) {
        final Grid grid = new Grid(GridUtils.of(ResourceLines.list("/day20.txt"), ' '));
        final DonutMaze maze = new DonutMaze(grid);

        part1(maze);
        part2(maze);
    }

    private static void part1(DonutMaze maze) {
        final DonutMazeExplorer explorer = new DonutMazeExplorer(maze);
        System.out.println("Shortest route through the maze takes " + explorer.shortestRoute() + " steps.");
    }

    private static void part2(DonutMaze maze) {
        final RecursiveDonutMazeExplorer explorer = new RecursiveDonutMazeExplorer(maze);
        System.out.println("Shortest recursive route through the maze takes " + explorer.shortestRoute() + " steps.");
    }
}
