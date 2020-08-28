package com.putoet.day5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {
    @Test
    public void testCreateAddress() {
        assertEquals(Address.of(0).toInt(), 0);
        assertEquals(Address.of(99).toInt(), 99);

        try {
            Address.of(-2);
        } catch (IllegalArgumentException exc) {
            assertEquals("Address cannot be negative -2", exc.getMessage());
        }
    }

    @Test
    public void testAccessAddress() {
        assertEquals(Address.START_ADDRESS.increase(5).toInt(), Address.of(5).toInt());
    }
}
