package com.putoet.day7;

import com.putoet.intcode.FixedMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.IntCodeInputOutputDevice;

import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class FeedbackAmplifierArray implements AmplifierArray {
    public static final int ARRAY_SIZE = 5;

    private final CountDownLatch latch = new CountDownLatch(ARRAY_SIZE);
    private final IntCodeDevice[] amplifiers = new IntCodeDevice[ARRAY_SIZE];
    private final IntCodeInputOutputDevice[] input = new IntCodeInputOutputDevice[]{
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice()
    };

    private final IntCodeInputOutputDevice output;

    public FeedbackAmplifierArray(List<Integer> intCodeProgram, PhaseSetting phaseSetting) {
        assert phaseSetting.size() == ARRAY_SIZE;

        for (int i = 0; i < amplifiers.length; i++) {
            amplifiers[i] = IntCodeComputer.builder()
                    .memory(new FixedMemory(intCodeProgram))
                    .input(input[i])
                    .output(input[i == ARRAY_SIZE - 1 ? 0 : i + 1])
                    .timeout(100, TimeUnit.MILLISECONDS)
                    .latch(latch)
                    .build();

            input[i].offer((long) phaseSetting.get(i));
        }
        output = input[0];
    }

    @Override
    public void run() {
        try {
            for (IntCodeDevice amplifier : amplifiers) {
                final Thread thread = new Thread(amplifier);
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
