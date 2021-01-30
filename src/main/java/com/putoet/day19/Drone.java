package com.putoet.day19;

import com.putoet.grid.Point;
import com.putoet.intcode.ExpandableMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.IntCodeInputOutputDevice;

import java.util.List;
import java.util.OptionalLong;

public class Drone {
    enum State {
        STATIONARY, PULLED
    }

    private final List<Long> intCode;

    public Drone(List<Long> intCode) {
        this.intCode = intCode;
    }

    public State state(Point point) {
        final ExpandableMemory memory = new ExpandableMemory(intCode);
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        final IntCodeInputOutputDevice output = new IntCodeInputOutputDevice();
        final IntCodeDevice device = IntCodeComputer.builder().memory(memory).input(input).output(output).build();

        input.offer(point.x);
        input.offer(point.y);
        device.run();

        final OptionalLong state = output.poll();
        if (state.isEmpty())
            throw new IllegalStateException("No state reported by drone");
        if (state.getAsLong() < 0 || state.getAsLong() > 1)
            throw new IllegalStateException("Invallid state reported by drone: " + state.getAsLong());

        return state.getAsLong() == 0L ? State.STATIONARY : State.PULLED;
    }
}
