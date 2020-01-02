package com.putoet.day11;

import com.putoet.day9.*;

public class Day11a {
    public static void main(String[] args) {
        final IMemory memory = MemoryLoader.fromFile("day11.txt");
        final Surface surface = new Surface();
        final IInputDevice camera = new Camera(surface);
        final IOutputDevice painter = new Painter(surface);
        final Processor processor = new Processor(memory, camera, painter);
        processor.run();

        System.out.println("Number of painted panels is " + surface.paintedPanelsCount());
        System.out.println(surface);
    }
}
