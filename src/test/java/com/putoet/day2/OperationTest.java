package com.putoet.day2;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperationTest {
    @Test
    public void testCreateOperation() {
        assertEquals(Operation.SUM, Operation.of(1));
        assertEquals(Operation.PRODUCT, Operation.of(2));
        assertEquals(Operation.EXIT, Operation.of(99));

        assertThrows(IllegalArgumentException.class, () -> Operation.of(-1));
    }

    @Test
    public void testOperationFunction() {
        final BiFunction<Integer, Integer, Integer> sum = Operation.SUM.biFunction();
        assertEquals(sum.apply(1, 2), Integer.valueOf(3));

        final BiFunction<Integer, Integer, Integer> product = Operation.PRODUCT.biFunction();
        assertEquals(product.apply(2, 2), Integer.valueOf(4));

        assertThrows(IllegalArgumentException.class, Operation.EXIT::biFunction);
    }

    @Test
    public void testOperationOperants() {
        assertEquals(1, Operation.EXIT.size());
        assertEquals(4, Operation.PRODUCT.size());
        assertEquals(4, Operation.SUM.size());
    }
}
