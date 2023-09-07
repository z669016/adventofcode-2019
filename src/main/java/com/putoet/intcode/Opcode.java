package com.putoet.intcode;

public class Opcode {
    private final int opcode;
    private final Mode[] mode = new Mode[3];

    public Opcode(long opcode) {
        assert opcode > 0;

        this.opcode = (int) opcode % 100;

        opcode = opcode / 100;
        for (int  i = 0; i < 3; i++) {
            assert opcode % 10 < Mode.values().length;

            mode[i] = Mode.values()[(int) opcode % 10];
            opcode = opcode / 10;
        }
    }

    public int opcode() { return opcode; }
    public Mode mode1() { return mode[0]; }
    public Mode mode2() { return mode[1]; }
    public Mode mode3() { return mode[2]; }

    @Override
    public String toString() {
        return String.format("%s%s%s%02d", mode3(), mode2(), mode1(), opcode());
    }
}
