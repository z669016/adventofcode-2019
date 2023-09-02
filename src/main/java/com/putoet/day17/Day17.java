package com.putoet.day17;

import com.putoet.grid.Grid;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    public static void main(String[] args) {
        final List<Long> intCode = new ArrayList<>(CSV.flatList("/day17.txt", Long::parseLong));
        final Grid grid = Timer.run(() -> part1(intCode));

        Timer.run(() -> part2(intCode, grid));
    }

    private static Grid part1(List<Long> intCode) {
        final var robot = new VacuumRobot(intCode);
        robot.run();

        final var grid = robot.scan();
        final var calibrator = new Calibrator(grid);
        System.out.println("the sum of the alignment parameters for the scaffold intersections is " +
            Calibrator.alignment(calibrator.intersections()));

        return grid;
    }

    private static void part2(List<Long> intCode, Grid grid) {
        final var calibrator = new Calibrator(grid);
        final var route = new Route(calibrator.route());
        final var robot = new VacuumRobot(intCode, route);
        robot.run();
        System.out.println("the amount of dust the vacuum robot has collected is " + robot.collectedDust());
    }
}
