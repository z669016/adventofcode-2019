package com.putoet.day17;

import com.putoet.grid.Grid;
import com.putoet.intcode.*;

import java.util.List;

public class VacuumRobot implements Runnable {
    private final List<Long> intCode;
    private final IntCodeInputOutputDevice inputDevice;
    private IntCodeInputOutputDevice outputDevice;

    public VacuumRobot(List<Long> intCode) {
        this.intCode = intCode;
        this.inputDevice = new IntCodeInputOutputDevice();
    }

    public VacuumRobot(List<Long> intCode, Route route) {
        this.intCode = intCode;
        this.inputDevice = new IntCodeInputOutputDevice();

        intCode.set(0, 2L);

        route.mainRoute().chars().forEach(inputDevice::offer);
        inputDevice.offer('\n');
        route.functionA().chars().forEach(inputDevice::offer);
        inputDevice.offer('\n');
        route.functionB().chars().forEach(inputDevice::offer);
        inputDevice.offer('\n');
        route.functionC().chars().forEach(inputDevice::offer);
        inputDevice.offer('\n');
        inputDevice.offer('n');
        inputDevice.offer('\n');
    }

    @Override
    public void run() {
        final Memory memory = new ExpandableMemory(intCode);
        outputDevice = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder()
                .memory(memory)
                .input(inputDevice)
                .output(outputDevice)
                .build();

        device.run();
    }

    public Grid scan() {
        if (outputDevice == null)
            throw new IllegalStateException("The vacuum robot didn;t run yet");

        final List<Long> scan = outputDevice.asList();
        final int width = scan.indexOf(10L) + 1;
        final int height = scan.size() / width;

        final char[][] grid = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                grid[y][x] = (char) scan.get(y * width + x).intValue();
            }
        }
        return new Grid(grid);
    }

    public long collectedDust() {
        if (outputDevice == null)
            throw new IllegalStateException("The vacuum robot didn;t run yet");

        final List<Long> output = outputDevice.asList();
        return output.get(output.size() - 1);
    }
}
