package com.putoet.intcode;

public enum Mode {
    POSITION,
    IMMEDIATE,
    RELATIVE;

    @Override
    public String toString() {
        return String.valueOf(this.ordinal());
    }
}
