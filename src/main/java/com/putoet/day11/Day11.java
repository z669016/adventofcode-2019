package com.putoet.day11;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day11.txt", Long::parseLong);

        Timer.run(() -> part1(intCode));
        Timer.run(() -> part2(intCode));
    }

    private static void part1(List<Long> intCode) {
        final var surface = new Surface();
        run(surface, intCode);

        System.out.println("Number of painted panels is " + surface.paintedPanelsCount());
    }

    private static void part2(List<Long> intCode) {
        final var surface = new Surface();
        surface.paint(PanelColor.WHITE);
        run(surface, intCode);

        System.out.println(surface);
        // PFKHECZU
    }

    private static void run(Surface surface, List<Long> intCode) {
        final var camera = new Camera(surface);
        final var painter = new Painter(surface);
        final var device = IntCodeComputer.builder()
                .memory(new ExpandableMemory(intCode))
                .input(camera)
                .output(painter)
                .build();

        device.run();
    }
}
