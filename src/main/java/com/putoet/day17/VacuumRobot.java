package com.putoet.day17;

import com.putoet.grid.Grid;
import com.putoet.intcode.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class VacuumRobot implements Runnable {
    private final List<Long> intCode;
    private final IntCodeInputOutputDevice inputDevice;
    private IntCodeInputOutputDevice outputDevice;

    public VacuumRobot(@NotNull List<Long> intCode) {
        this.intCode = intCode;
        this.inputDevice = new IntCodeInputOutputDevice();
    }

    public VacuumRobot(@NotNull List<Long> intCode, @NotNull Route route) {
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
        final var memory = new ExpandableMemory(intCode);
        outputDevice = new IntCodeInputOutputDevice();
        final var device = IntCodeComputer.builder()
                .memory(memory)
                .input(inputDevice)
                .output(outputDevice)
                .build();

        device.run();
    }

    public Grid scan() {
        if (outputDevice == null)
            throw new IllegalStateException("The vacuum robot didn't run yet");

        final var scan = outputDevice.asList();
        final var width = scan.indexOf(10L) + 1;
        final var height = scan.size() / width;

        final var grid = new char[height][width];
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width - 1; x++) {
                grid[y][x] = (char) scan.get(y * width + x).intValue();
            }
        }
        return new Grid(grid);
    }

    public long collectedDust() {
        if (outputDevice == null)
            throw new IllegalStateException("The vacuum robot didn't run yet");

        final var output = outputDevice.asList();
        return output.get(output.size() - 1);
    }
}
