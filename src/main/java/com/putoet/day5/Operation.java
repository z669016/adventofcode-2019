package com.putoet.day5;

import java.util.function.BiFunction;

public enum Operation {
    SUM(1, 4),
    PRODUCT(2, 4),
    INPUT(3, 2),
    OUTPUT(3, 2),
    EXIT(99, 1);

    private final int opcode;
    private final int size;

    private Operation(int opcode, int size) {
        this.opcode = opcode;
        this.size = size;
    }

    public static Operation of(int opcode) {
        opcode = opcode % 100;

        final Operation operation;
        switch (opcode) {
            case 1:
                operation = SUM; break;
            case 2:
                operation = PRODUCT; break;
            case 3:
                operation = INPUT; break;
            case 4:
                operation = OUTPUT; break;
            case 99:
                operation = EXIT; break;
            default:
                throw new IllegalArgumentException("Illegal opcode " + opcode);
        }

        return operation;
    }

    public int size() {
        return this.size;
    }
}
