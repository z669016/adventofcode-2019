package com.putoet.day7;

import com.putoet.intcode.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

class SimpleAmplifierArray implements AmplifierArray {
    private static final int ARRAY_SIZE = 5;

    private final IntCodeDevice[] amplifiers = new IntCodeDevice[ARRAY_SIZE];
    private final IntCodeConcurrentInputOutputDevice[] input = new IntCodeConcurrentInputOutputDevice[]{
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice()
    };

    private final IntCodeConcurrentInputOutputDevice output;

    public SimpleAmplifierArray(@NotNull List<Long> intCodeProgram, @NotNull PhaseSetting phaseSetting) {
        assert phaseSetting.size() == ARRAY_SIZE;

        for (int i = 0; i < amplifiers.length; i++) {
            amplifiers[i] = ConcurrentIntCodeComputer.builder()
                    .memory(new FixedMemory(intCodeProgram))
                    .input(input[i])
                    .output(input[i + 1])
                    .timeout(100, TimeUnit.MILLISECONDS)
                    .build();

            input[i].offer(phaseSetting.get(i));
        }
        output = input[input.length - 1];
    }

    @Override
    public void run() {
        for (var amplifier : amplifiers) {
            final var thread = new Thread(amplifier);
            thread.start();
        }
    }

    @Override
    public void input(long input) {
        this.input[0].offer(input);
    }

    @Override
    public OptionalLong output(int timeout, TimeUnit timeUnit) {
        return output.poll(timeout, timeUnit);
    }
}
