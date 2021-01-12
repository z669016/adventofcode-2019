package com.putoet.day11;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;

import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        final Surface surface = new Surface();
        run(surface);

        System.out.println("Number of painted panels is " + surface.paintedPanelsCount());
    }

    private static void part2() {
        final Surface surface = new Surface();
        surface.paint(PanelColor.WHITE);

        run(surface);

        System.out.println(surface);
        // PFKHECZU
    }

    private static void run(Surface surface) {
        final List<Long> intCode = CSV.flatList("/day11.txt", Long::parseLong);
        final InputDevice camera = new Camera(surface);
        final OutputDevice painter = new Painter(surface);
        final IntCodeDevice device = IntCodeComputer.builder()
                .memory(new ExpandableMemory(intCode))
                .input(camera)
                .output(painter)
                .build();

        device.run();
    }
}
