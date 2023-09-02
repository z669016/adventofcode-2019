package com.putoet.day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorATest {
    @Test
    public void testGeneratorStart() {
        final var generatorA = new GeneratorA();
        assertTrue(generatorA.hasNext());
        assertEquals("111111", generatorA.next().toString());
        assertTrue(generatorA.hasNext());
        assertEquals("111112", generatorA.next().toString());
        assertEquals("111113", generatorA.next().toString());
        assertEquals("111114", generatorA.next().toString());
        assertEquals("111115", generatorA.next().toString());
        assertEquals("111116", generatorA.next().toString());
        assertEquals("111117", generatorA.next().toString());
        assertEquals("111118", generatorA.next().toString());
        assertEquals("111119", generatorA.next().toString());
        assertEquals("111122", generatorA.next().toString());
    }

    @Test
    public void testGeneratorEnd() {
        final var generatorA = new GeneratorA(9);
        assertTrue(generatorA.hasNext());
        assertEquals("999999", generatorA.next().toString());
        assertFalse(generatorA.hasNext());
    }

    @Test
    public void testGeneratorMinMax() {
        var generatorA = new GeneratorA(123456, 999999);
        assertTrue(generatorA.hasNext());
        assertEquals("123466", generatorA.next().toString());

        generatorA = new GeneratorA(111111, 999999);
        assertTrue(generatorA.hasNext());
        assertEquals("111111", generatorA.next().toString());

        generatorA = new GeneratorA(999989, 999999);
        assertTrue(generatorA.hasNext());
        assertEquals("999999", generatorA.next().toString());
        assertFalse(generatorA.hasNext());
    }
}
