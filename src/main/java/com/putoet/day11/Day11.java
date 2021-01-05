package com.putoet.day11;

import com.putoet.day9.IInputDevice;
import com.putoet.day9.IMemory;
import com.putoet.day9.IOutputDevice;
import com.putoet.day9.Processor;

public class Day11 {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    private static void partOne() {
        final Surface surface = new Surface();
        run(surface);

        System.out.println("Number of painted panels is " + surface.paintedPanelsCount());
        //System.out.println(surface);
    }

    private static void partTwo() {
        final Surface surface = new Surface();
        surface.paint(Color.WHITE);

        run(surface);

        System.out.println("Number of painted panels is " + surface.paintedPanelsCount());
        System.out.println(surface);
        // PFKHECZU
    }

    private static void run(Surface surface) {
        final IMemory memory = MemoryLoader.fromFile("/day11.txt");
        final IInputDevice camera = new Camera(surface);
        final IOutputDevice painter = new Painter(surface);
        final Processor processor = new Processor(memory, camera, painter);

        processor.run();
    }
}
