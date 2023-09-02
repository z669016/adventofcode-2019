package com.putoet.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MassTest {
    @Test
    public void testCalculateFuelForMass() {
        assertEquals(2, new Mass(12).requiredFuel().fuel());
        assertEquals(2, new Mass("14").requiredFuel().fuel());
        assertEquals(966, new Mass(1969).requiredFuel().fuel());
        assertEquals(50346, new Mass("100756").requiredFuel().fuel());
    }
}
