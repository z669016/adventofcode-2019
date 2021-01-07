package com.putoet.intcode;

public interface Instruction extends Runnable {
    int size();
    Opcode opcode();
    void run();
    void next(IntCodeDevice device);

    int EXIT = 99;
}
