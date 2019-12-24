package com.putoet.day5;

import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;

public class OperationTest {
    @Test
    public void testCreateOperation() {
        assertEquals(Operation.SUM, Operation.of(1));
        assertEquals(Operation.SUM, Operation.of(1001));
        assertEquals(Operation.PRODUCT, Operation.of(2));
        assertEquals(Operation.PRODUCT, Operation.of(102));
        assertEquals(Operation.EXIT, Operation.of(99));
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

        try {
            Operation.of(-1);
        } catch (IllegalArgumentException exc) {
            assertEquals("Illegal opcode -1", exc.getMessage());
        }
    }

    @Test
    public void testOperationOperants() {
        assertEquals(1, Operation.EXIT.size());
        assertEquals(4, Operation.PRODUCT.size());
        assertEquals(4, Operation.SUM.size());
        assertEquals(2, Operation.INPUT.size());
        assertEquals(2, Operation.OUTPUT.size());
    }

}
