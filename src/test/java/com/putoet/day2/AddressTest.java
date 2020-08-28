package com.putoet.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressTest {

    @Test
    public void testCreateAddress() {
        assertEquals(Address.of(0).toInt(), 0);
        assertEquals(Address.of(99).toInt(), 99);

        assertThrows(AssertionError.class, () -> Address.of(-2));
    }

    @Test
    public void testAccessAddress() {
        assertEquals(Address.START_ADDRESS.increase(5).toInt(), Address.of(5).toInt());
    }
}
