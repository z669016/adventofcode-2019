package com.putoet.intcode;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

public interface IntCodeDevice extends Runnable {
    Address relativeBase();

    void relativeBase(long offset);

    Address ip();

    void ip(long ip);

    void next(long offset);

    InputDevice input();
    void input(InputDevice input);

    OutputDevice output();
    void output(OutputDevice output);

    Memory memory();

    PrintStream printStream();

    int timeout();

    TimeUnit timeUnit();

    boolean resumable();

    void blockForInput();

    boolean isBlockedForInput();

    IntCodeDevice copy();
}
