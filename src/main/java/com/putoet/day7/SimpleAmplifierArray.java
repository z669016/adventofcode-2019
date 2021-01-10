package com.putoet.day7;

import com.putoet.intcode.FixedMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;

import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class SimpleAmplifierArray implements AmplifierArray {
    private static final int ARRAY_SIZE = 5;

    private final IntCodeDevice[] amplifiers = new IntCodeDevice[ARRAY_SIZE];
    private final BlockingDeque<Long>[] input = new BlockingDeque[]{
            new LinkedBlockingDeque<Long>(),
            new LinkedBlockingDeque<Long>(),
            new LinkedBlockingDeque<Long>(),
            new LinkedBlockingDeque<Long>(),
            new LinkedBlockingDeque<Long>(),
            new LinkedBlockingDeque<Long>()
    };

    private final BlockingDeque<Long> output;

    public SimpleAmplifierArray(List<Integer> intCodeProgram, PhaseSetting phaseSetting) {
        assert phaseSetting.size() == ARRAY_SIZE;

        for (int i = 0; i < amplifiers.length; i++) {
            amplifiers[i] = IntCodeComputer.builder()
                    .memory(new FixedMemory(intCodeProgram))
                    .input(input[i])
                    .output(input[i + 1])
                    .timeout(100, TimeUnit.MILLISECONDS)
                    .build();

            input[i].offer((long) phaseSetting.get(i));
        }
        output = input[input.length - 1];
    }

    @Override
    public void run() {
        for (IntCodeDevice amplifier : amplifiers) {
            final Thread thread = new Thread(amplifier);
            thread.start();
        }
    }

    @Override
    public void input(long input) {
        this.input[0].offer(input);
    }

    @Override
    public OptionalLong output(int timeout, TimeUnit timeUnit) {
        try {
            return OptionalLong.of(Objects.requireNonNull(output.poll(timeout, timeUnit)));
        } catch (InterruptedException ignored) {
        }

        return OptionalLong.empty();
    }
}
