package com.putoet.day19;

import com.putoet.grid.Point;
import com.putoet.intcode.ExpandableMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.IntCodeInputOutputDevice;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalLong;

class Drone {
    enum State {
        STATIONARY, PULLED
    }

    private final List<Long> intCode;

    public Drone(@NotNull List<Long> intCode) {
        this.intCode = intCode;
    }

    public State state(@NotNull Point point) {
        final var memory = new ExpandableMemory(intCode);
        final var input = new IntCodeInputOutputDevice();
        final var output = new IntCodeInputOutputDevice();
        final var device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(point.x());
        input.offer(point.y());
        device.run();

        final var state = output.poll().orElseThrow();
        if (state < 0 || state > 1)
            throw new IllegalStateException("Invalid state reported by drone: " + state);

        return state == 0L ? State.STATIONARY : State.PULLED;
    }
}
