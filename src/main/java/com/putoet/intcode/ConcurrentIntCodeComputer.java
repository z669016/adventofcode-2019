package com.putoet.intcode;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConcurrentIntCodeComputer extends IntCodeComputer implements ConcurrentIntCodeDevice {
    private final CountDownLatch latch;

    ConcurrentIntCodeComputer(Memory memory,
                              InputDevice input,
                              OutputDevice output,
                              PrintStream printStream,
                              int timeout,
                              TimeUnit timeUnit,
                              CountDownLatch latch) {
        super(memory, input, output, printStream, timeout, timeUnit, false);
        this.latch = latch;
    }

    public static IntCodeDeviceFactory builder() {
        return new IntCodeDeviceFactory();
    }

    @Override
    public void run() {
        super.run();
        latch.countDown();
    }

    @Override
    public CountDownLatch latch() {
        return latch;
    }

    @Override
    public synchronized void input(InputDevice input) {
        super.input(input);
    }

    @Override
    public synchronized void output(OutputDevice output) {
        super.output(output);
    }

    @Override
    public IntCodeComputer copy() {
        throw new UnsupportedOperationException("A concurrent IntCodeDevice cannot be copied safely");
    }
}
