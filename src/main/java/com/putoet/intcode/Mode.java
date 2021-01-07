package com.putoet.intcode;

public enum Mode {
    IMMEDIATE,
    POSITION;

    @Override
    public String toString() {
        return this == IMMEDIATE ? "1" : "0";
    }
}
