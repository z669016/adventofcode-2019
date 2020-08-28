package com.putoet.day2;

import java.util.function.BiFunction;

public enum Operation {
    SUM(1, 4),
    PRODUCT(2, 4),
    EXIT(99, 1);

    private final int opcode;
    private final int size;

    Operation(int opcode, int size) {
        this.opcode = opcode;
        this.size = size;
    }

    static Operation of(int opcode) {
        return switch (opcode) {
            case 1 -> SUM;
            case 2 -> PRODUCT;
            case 99 -> EXIT;
            default -> throw new IllegalArgumentException("Illegal opcode " + opcode);
        };
    }

    BiFunction<Integer, Integer, Integer> biFunction() {
        return switch (this) {
            case SUM -> Integer::sum;
            case PRODUCT -> (o1, o2) -> o1 * o2;
            default -> throw new IllegalArgumentException("NOP");
        };
    }

    int size() {
        return this.size;
    }
}
