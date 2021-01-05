package com.putoet.intcode;

import com.putoet.intcode.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void increase() {
        assertEquals(10, new Address(3).increase(7).intValue());
    }

    @Test
    void equals() {
        assertEquals(Address.START, new Address(0));
    }
}