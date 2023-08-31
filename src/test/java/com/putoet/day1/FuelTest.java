package com.putoet.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuelTest {

    @Test
    public void testAdd() {
        assertEquals(5, new Fuel(2).add(new Fuel(3)).fuel());
    }
}
