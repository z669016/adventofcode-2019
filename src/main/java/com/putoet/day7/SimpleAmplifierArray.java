package com.putoet.day7;

import com.putoet.intcode.FixedMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.IntCodeInputOutputDevice;

import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

public class SimpleAmplifierArray implements AmplifierArray {
    private static final int ARRAY_SIZE = 5;

    private final IntCodeDevice[] amplifiers = new IntCodeDevice[ARRAY_SIZE];
    private final IntCodeInputOutputDevice[] input = new IntCodeInputOutputDevice[]{
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice(),
            new IntCodeInputOutputDevice()
    };

    private final IntCodeInputOutputDevice output;

    public SimpleAmplifierArray(List<Long> intCodeProgram, PhaseSetting phaseSetting) {
        assert phaseSetting.size() == ARRAY_SIZE;

        for (int i = 0; i < amplifiers.length; i++) {
            amplifiers[i] = IntCodeComputer.builder()
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
        return output.poll(timeout, timeUnit);
    }
}
