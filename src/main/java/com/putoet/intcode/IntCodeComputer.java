package com.putoet.intcode;

import java.io.PrintStream;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class IntCodeComputer implements IntCodeDevice {
    private final CountDownLatch latch;
    private final BlockingDeque<Long> input;
    private final Queue<Long> output;
    private final Memory memory;
    private final PrintStream printStream;
    private final int timeout;
    private final TimeUnit timeUnit;

    private Address ip = Address.START;

    private IntCodeComputer(Memory memory, BlockingDeque<Long> input, Queue<Long> output,
                            PrintStream printStream, int timeout, TimeUnit timeUnit, CountDownLatch latch) {
        this.memory = memory;
        this.input = input;
        this.output = output;
        this.printStream = printStream;

        if (timeUnit != null) {
            this.timeout = timeout;
            this.timeUnit = timeUnit;
        } else {
            this.timeout = 100;
            this.timeUnit = TimeUnit.MILLISECONDS;
        }

        this.latch = latch;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void run() {
        final Interpreter interpreter = new Interpreter(this);

        while (interpreter.hasNext()) {
            final Instruction instruction = interpreter.next();
            instruction.run();
        }

        if (latch != null)
            latch.countDown();
    }

    @Override
    public Address ip() {
        return ip;
    }

    @Override
    public void ip(long ip) {
        assert ip >= Integer.MIN_VALUE && ip <= Integer.MAX_VALUE;

        this.ip = new Address(ip);
    }

    @Override
    public void next(long offset) {
        assert offset >= Integer.MIN_VALUE && offset <= Integer.MAX_VALUE;

        ip = ip.increase((int) offset);
    }

    @Override
    public BlockingDeque<Long> input() {
        return input;
    }

    @Override
    public Queue<Long> output() {
        return output;
    }

    @Override
    public Memory memory() {
        return memory;
    }

    @Override
    public PrintStream printStream() {
        return printStream;
    }

    @Override
    public int timeout() {
        return timeout;
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }

    @Override
    public CountDownLatch latch() {
        return latch;
    }

    public static class Builder {
        private Memory memory;
        private BlockingDeque<Long> input;
        private Queue<Long> output;
        private PrintStream printStream;
        private int timeout;
        private TimeUnit timeUnit;
        private CountDownLatch latch;

        private Builder() {
        }

        public Builder memory(Memory memory) {
            this.memory = memory;
            return this;
        }

        public Builder input(BlockingDeque<Long> input) {
            this.input = input;
            return this;
        }

        public Builder output(Queue<Long> output) {
            this.output = output;
            return this;
        }

        public Builder printStream(PrintStream printStream) {
            this.printStream = printStream;
            return this;
        }

        public Builder timeout(int timeout, TimeUnit timeUnit) {
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder latch(CountDownLatch latch) {
            this.latch = latch;
            return this;
        }

        public IntCodeDevice build() {
            return new IntCodeComputer(memory, input, output, printStream, timeout, timeUnit, latch);
        }
    }
}
