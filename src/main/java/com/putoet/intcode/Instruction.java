package com.putoet.intcode;

public interface Instruction extends Runnable {
    int size();
    int opcode();
    void run();

    int EXIT = 99;
}
