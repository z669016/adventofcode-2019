package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.resources.ResourceLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day24 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day24.txt");
        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        final Set<Long> history = new HashSet<>();
        Eris next = new Eris(new Grid(GridUtils.of(lines)));

        while (history.add(next.biodiversityRating()))
            next = next.evolve();

        System.out.println("The biodiversity rating for the first layout that appears twice is " + next.biodiversityRating());
    }

    private static void part2(List<String> lines) {
        RecursiveFoldedEris next = new RecursiveFoldedEris(GridUtils.of(lines));

        for (int i = 0; i < 200; i++)
            next = next.evolve();

        System.out.println("The bugs are present in the recursive folded Eris after 200 minutes is " + next.bugs());
    }
}
