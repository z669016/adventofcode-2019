package com.putoet.intcode;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class IntCodeDeviceFactory {
    private Memory memory;
    private InputDevice input;
    private OutputDevice output;
    private PrintStream printStream;
    private int timeout;
    private TimeUnit timeUnit;
    private CountDownLatch latch;
    private boolean resumable = false;

    public IntCodeDeviceFactory() {
    }

    public IntCodeDeviceFactory memory(Memory memory) {
        this.memory = memory;
        return this;
    }

    public IntCodeDeviceFactory input(InputDevice input) {
        this.input = input;
        return this;
    }

    public IntCodeDeviceFactory output(OutputDevice output) {
        this.output = output;
        return this;
    }

    public IntCodeDeviceFactory printStream(PrintStream printStream) {
        this.printStream = printStream;
        return this;
    }

    public IntCodeDeviceFactory timeout(int timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        return this;
    }

    public IntCodeDeviceFactory latch(CountDownLatch latch) {
        this.latch = latch;
        return this;
    }

    public IntCodeDeviceFactory resumable() {
        this.resumable = true;
        return this;
    }

    public IntCodeDevice build() {
        if (latch != null && resumable)
            throw new IllegalStateException("A concurrent IntCodeDevice cannot be resumable.");

        if (latch != null)
            return new ConcurrentIntCodeComputer(memory, input, output, printStream, timeout, timeUnit, latch);

        return new IntCodeComputer(memory, input, output, printStream, timeout, timeUnit, resumable);

    }
}
