package com.putoet.day5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InputDeviceTest {
    @Test
    public void testInputDevice() {
        assertThrows(AssertionError.class, () -> new InputDevice(null));

        assertThrows(IllegalStateException.class, () -> {
            final InputDevice id = new InputDevice(List.of());
            id.get().get();
        });

        final InputDevice id = new InputDevice(List.of(1, 2, 3));
        assertEquals("[1, 2, 3]", id.toString());

        assertEquals(Integer.valueOf(1), id.get().get());
        assertEquals(Integer.valueOf(2), id.get().get());
        assertEquals(Integer.valueOf(3), id.get().get());

        assertThrows(IllegalStateException.class, () -> id.get());

        assertEquals("[]", id.toString());
    }

}
