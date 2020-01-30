package com.putoet.day15;

import com.putoet.day11.MemoryLoader;
import com.putoet.day9.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Day15 {
    public static void main(String[] args) {
        final IMemory memory = MemoryLoader.fromFile("day15.txt");
        final IInputDevice inputDevice = new ResumableInputDevice(List.of());
        final IOutputDevice outputDevice = new BlockingOutputDevice();
        final Processor processor = new Processor(memory, inputDevice, outputDevice);

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(processor);

        final List<Direction> trace = new ArrayList<>();
        final Navigator navigator = new Navigator(inputDevice, trace);

        final IExtendableSurface surface = new OriginExtendableSurface();
        final Sensor sensor = new Sensor(outputDevice);
        final Explorer explorer = new Explorer(surface, navigator, sensor);

        explorer.explore();
    }
}
