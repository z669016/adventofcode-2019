package com.putoet.intcode;

import java.io.PrintStream;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

public interface IntCodeDevice extends Runnable {
    Address ip();
    void ip(long ip);

    void next(long offset);

    BlockingDeque<Long> input();

    Queue<Long> output();

    Memory memory();

    PrintStream printStream();

    int timeout();

    TimeUnit timeUnit();
}
