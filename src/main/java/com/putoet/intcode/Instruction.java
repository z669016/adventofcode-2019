package com.putoet.intcode;

public interface Instruction extends Runnable {
    int size();
    Opcode opcode();
    void run();
    void next(IntCodeDevice device);

    int ADD = 1;
    int MUL = 2;
    int IN = 3;
    int OUT = 4;
    int JIT = 5;
    int JIF = 6;
    int LT = 7;
    int EQ = 8;
    int EXIT = 99;
}
