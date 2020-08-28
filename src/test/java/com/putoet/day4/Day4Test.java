package com.putoet.day4;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day4Test {
    @Test
    public void testGeneratorStart() {
        GeneratorA generatorA = new GeneratorA();
        assertTrue(generatorA.hasNext());
        assertEquals ("111111", generatorA.next());
        assertTrue(generatorA.hasNext());
        assertEquals ("111112", generatorA.next());
        assertEquals ("111113", generatorA.next());
        assertEquals ("111114", generatorA.next());
        assertEquals ("111115", generatorA.next());
        assertEquals ("111116", generatorA.next());
        assertEquals ("111117", generatorA.next());
        assertEquals ("111118", generatorA.next());
        assertEquals ("111119", generatorA.next());
        assertEquals ("111122", generatorA.next());
    }

    @Test
    public void testGeneratorEnd() {
        GeneratorA generatorA = new GeneratorA(9);
        assertTrue(generatorA.hasNext());
        assertEquals ("999999", generatorA.next());
        assertFalse(generatorA.hasNext());
    }

    @Test
    public void testGeneratorMinMax() {
        GeneratorA generatorA = new GeneratorA(123456, 999999);
        assertTrue(generatorA.hasNext());
        assertEquals ("123466", generatorA.next());

        generatorA = new GeneratorA(111111, 999999);
        assertTrue(generatorA.hasNext());
        assertEquals ("111111", generatorA.next());

        generatorA = new GeneratorA(999989, 999999);
        assertTrue(generatorA.hasNext());
        assertEquals ("999999", generatorA.next());
        assertFalse(generatorA.hasNext());
    }

    @Test
    public void testGeneratorB() {
        GeneratorB generatorB = new GeneratorB(112233, 999999);
        assertEquals ("112233", generatorB.next());

        generatorB = new GeneratorB(123444, 999999);
        assertEquals ("123445", generatorB.next());

        generatorB = new GeneratorB(111122, 999999);
        assertEquals ("111122", generatorB.next());
    }
}
