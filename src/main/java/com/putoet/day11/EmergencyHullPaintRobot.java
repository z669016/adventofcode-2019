package com.putoet.day11;

import com.putoet.day9.*;

import java.util.List;

public class EmergencyHullPaintRobot implements Runnable {
    private final IInputDevice inputDevice;
    private final IOutputDevice outputDevice;
    private final IMemory memory;
    private final Processor processor;

    public EmergencyHullPaintRobot(List<Integer> intCode, IInputDevice camera, IOutputDevice painter) {
        inputDevice = null;
        outputDevice = painter;
        memory = Memory.ofIntegerList(intCode);
        processor = new Processor(memory, camera, painter);
    }

    @Override
    public void run() {
        processor.run();
    }
}
