package com.putoet.day17;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalibratorTest {

    @Test
    void intersections() {
        final List<String> lines = ResourceLines.list("/day17.txt");
        final Grid grid = new Grid(GridUtils.of(lines));
        final Calibrator calibrator = new Calibrator(grid);

        final List<Point> intersections = calibrator.intersections();
        assertEquals(4, intersections.size());
        assertEquals(276, Calibrator.alignment(intersections));
    }

    @Test
    void route() {
        final List<String> lines = ResourceLines.list("/day17.txt");
        final Grid grid = new Grid(GridUtils.of(lines));
        final Calibrator calibrator = new Calibrator(grid);

        assertEquals(List.of(
                "R","8","R","8","R","4","R","4","R","8","L","6","L","2",
                "R","4","R","4","R","8","R","8","R","8","L","6","L","2"), calibrator.route());
    }
}