package com.putoet.intcode;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public interface IntCodeDevice extends Runnable {
    Address relativeBase();

    void relativeBase(long offset);

    Address ip();

    void ip(long ip);

    void next(long offset);

    InputDevice input();

    OutputDevice output();

    Memory memory();

    PrintStream printStream();

    int timeout();

    TimeUnit timeUnit();

    CountDownLatch latch();

    boolean resumable();

    void blockForInput();

    boolean isBlockedForInput();
}
