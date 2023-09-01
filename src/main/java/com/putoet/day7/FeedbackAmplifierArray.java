package com.putoet.day7;

import com.putoet.intcode.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class FeedbackAmplifierArray implements AmplifierArray {
    public static final int ARRAY_SIZE = 5;

    private final CountDownLatch latch = new CountDownLatch(ARRAY_SIZE);
    private final IntCodeDevice[] amplifiers = new IntCodeDevice[ARRAY_SIZE];
    private final IntCodeConcurrentInputOutputDevice[] input = new IntCodeConcurrentInputOutputDevice[]{
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice(),
            new IntCodeConcurrentInputOutputDevice()
    };

    private final IntCodeConcurrentInputOutputDevice output;

    public FeedbackAmplifierArray(@NotNull List<Long> intCodeProgram, @NotNull PhaseSetting phaseSetting) {
        assert phaseSetting.size() == ARRAY_SIZE;

        for (var i = 0; i < amplifiers.length; i++) {
            amplifiers[i] = ConcurrentIntCodeComputer.builder()
                    .memory(new FixedMemory(intCodeProgram))
                    .input(input[i])
                    .output(input[i == ARRAY_SIZE - 1 ? 0 : i + 1])
                    .timeout(100, TimeUnit.MILLISECONDS)
                    .latch(latch)
                    .build();

            input[i].offer(phaseSetting.get(i));
        }
        output = input[0];
    }

    @Override
    public void run() {
        try {
            for (var amplifier : amplifiers) {
                final var thread = new Thread(amplifier);
                thread.start();
            }
            latch.await();
        } catch (InterruptedException ignored) {
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
