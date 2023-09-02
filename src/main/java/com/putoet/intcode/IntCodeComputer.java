package com.putoet.intcode;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

public class IntCodeComputer implements IntCodeDevice {
    private final Memory memory;
    private InputDevice input;
    private OutputDevice output;
    private final PrintStream printStream;
    private final int timeout;
    private final TimeUnit timeUnit;
    private final boolean resumable;

    private Address ip = Address.START;
    private Address relativeBase = Address.START;
    private boolean blockedForInput = false;
    private Interpreter interpreter;

    private IntCodeComputer(IntCodeComputer device) {

        this.memory = device.memory.copy();
        this.input = device.input;
        this.output = device.output;
        this.printStream = device.printStream;
        this.timeout = device.timeout;
        this.timeUnit = device.timeUnit;
        this.resumable = device.resumable;

        this.ip = device.ip;
        this.relativeBase = device.relativeBase;
        this.blockedForInput = device.blockedForInput;
        this.interpreter = null;
    }

    IntCodeComputer(Memory memory,
                    InputDevice input,
                    OutputDevice output,
                    PrintStream printStream,
                    int timeout,
                    TimeUnit timeUnit,
                    boolean resumable) {
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

        this.resumable = resumable;
    }

    public IntCodeComputer copy() {
        return new IntCodeComputer(this);
    }

    public static IntCodeDeviceFactory builder() {
        return new IntCodeDeviceFactory();
    }

    @Override
    public void run() {
        if (interpreter == null || !blockedForInput) {
            ip = Address.START;
            interpreter = new Interpreter(this);
        }
        blockedForInput = false;

        while (interpreter.hasNext() && !blockedForInput && !Thread.interrupted()) {
            final Instruction instruction = interpreter.next();
            instruction.run();
        }
    }

    @Override
    public Address relativeBase() {
        return relativeBase;
    }

    @Override
    public void relativeBase(long offset) {
        assert offset >= Integer.MIN_VALUE && offset <= Integer.MAX_VALUE;

        relativeBase = relativeBase.increase((int) offset);
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
    public InputDevice input() {
        return input;
    }

    @Override
    public void input(InputDevice input) {
        this.input = input;
    }

    @Override
    public OutputDevice output() {
        return output;
    }

    @Override
    public void output(OutputDevice output) {
        this.output = output;
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
    public boolean resumable() {
        return resumable;
    }

    @Override
    public void blockForInput() {
        this.blockedForInput = true;
    }

    @Override
    public boolean isBlockedForInput() {
        return blockedForInput;
    }

}
