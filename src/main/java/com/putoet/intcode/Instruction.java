package com.putoet.intcode;

public interface Instruction extends Runnable {
    int size();
    Opcode opcode();
    void run();

    int EXIT = 99;
}
