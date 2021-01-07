package com.putoet.intcode;

import java.util.Queue;

public interface IntCodeDevice extends Runnable {
    Address ip();
    void ip(long ip);

    void next(long offset);

    Queue<Long> input();

    Queue<Long> output();

    Memory memory();
}
