package com.putoet.day9;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperationTest {
    @Test
    public void testCreateOperation() {
        assertEquals(Operation.SUM, Operation.of(1));
        assertEquals(Operation.SUM, Operation.of(1001));
        assertEquals(Operation.PRODUCT, Operation.of(2));
        assertEquals(Operation.PRODUCT, Operation.of(102));
        assertEquals(Operation.INPUT, Operation.of(3));
        assertEquals(Operation.OUTPUT, Operation.of(4));
        assertEquals(Operation.JUMP_IF_TRUE, Operation.of(5));
        assertEquals(Operation.JUMP_IF_TRUE, Operation.of(1005));
        assertEquals(Operation.JUMP_IF_FALSE, Operation.of(6));
        assertEquals(Operation.JUMP_IF_FALSE, Operation.of(106));
        assertEquals(Operation.LESS_THAN, Operation.of(7));
        assertEquals(Operation.LESS_THAN, Operation.of(1007));
        assertEquals(Operation.EQUAL, Operation.of(8));
        assertEquals(Operation.EQUAL, Operation.of(1008));
        assertEquals(Operation.ADJUST_RELATIVE_BASE, Operation.of(9));
        assertEquals(Operation.ADJUST_RELATIVE_BASE, Operation.of(109));
        assertEquals(Operation.EXIT, Operation.of(99));

        try {
            Operation.of(-1);
        } catch (IllegalArgumentException exc) {
            assertEquals("Illegal opcode -1", exc.getMessage());
        }
    }

    @Test
    public void testOperationSize() {
        assertEquals(4, Operation.PRODUCT.size());
        assertEquals(4, Operation.SUM.size());
        assertEquals(2, Operation.INPUT.size());
        assertEquals(2, Operation.OUTPUT.size());
        assertEquals(3, Operation.JUMP_IF_TRUE.size());
        assertEquals(3, Operation.JUMP_IF_FALSE.size());
        assertEquals(4, Operation.LESS_THAN.size());
        assertEquals(4, Operation.EQUAL.size());
        assertEquals(2, Operation.ADJUST_RELATIVE_BASE.size());
        assertEquals(1, Operation.EXIT.size());
    }

    @Test
    public void testToString() {
        assertEquals("EXIT", Operation.EXIT.toString());
    }

}
