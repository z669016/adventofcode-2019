package com.putoet.day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChemicalTest {
    @Test
    public void testChemical() {
        assertEquals("A", new Chemical("A").name());
        assertEquals("XYZ", new Chemical("xyz").name());
    }

    @Test
    public void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> new Chemical("a1b"));
    }
}