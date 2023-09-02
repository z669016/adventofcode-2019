package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.HashSet;
import java.util.List;

public class Day24 {
    public static void main(String[] args) {
        final var lines = ResourceLines.list("/day24.txt");
        Timer.run(() -> part1(lines));
        Timer.run(() -> part2(lines));
    }

    private static void part1(List<String> lines) {
        final var history = new HashSet<Long>();
        var next = new Eris(new Grid(GridUtils.of(lines)));

        while (history.add(next.biodiversityRating()))
            next = next.evolve();

        System.out.println("The biodiversity rating for the first layout that appears twice is " + next.biodiversityRating());
    }

    private static void part2(List<String> lines) {
        var next = new RecursiveFoldedEris(GridUtils.of(lines));

        for (var i = 0; i < 200; i++)
            next = next.evolve();

        System.out.println("The bugs are present in the recursive folded Eris after 200 minutes is " + next.bugs());
    }
}
