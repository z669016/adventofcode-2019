package com.putoet.day5;

public enum Operation {
    SUM(1, 4),
    PRODUCT(2, 4),
    INPUT(3, 2),
    OUTPUT(4, 2),
    JUMP_IF_TRUE(5, 3),
    JUMP_IF_FALSE(6, 3),
    LESS_THAN(7, 4),
    EQUAL(8, 4),
    EXIT(99, 1);

    private final int opcode;
    private final int size;

    private Operation(final int opcode, final int size) {
        this.opcode = opcode;
        this.size = size;
    }

    public static Operation of(final int opcode) {
        switch (opcode % 100) {
            case 1: return SUM;
            case 2: return PRODUCT;
            case 3: return INPUT;
            case 4: return OUTPUT;
            case 5: return JUMP_IF_TRUE;
            case 6: return JUMP_IF_FALSE;
            case 7: return LESS_THAN;
            case 8: return EQUAL;
            case 99: return EXIT;
            default:
                throw new IllegalArgumentException("Illegal opcode " + opcode);
        }
    }

    public int size() {
        return this.size;
    }
}
