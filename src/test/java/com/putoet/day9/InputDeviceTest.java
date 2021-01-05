package com.putoet.day9;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputDeviceTest {
    @Test
    public void testInputDevice() {
        assertThrows(IllegalArgumentException.class, () -> new InputDevice(null));

        assertThrows(IllegalStateException.class, () -> {
            final IInputDevice id = new InputDevice(List.of());
            id.get().get();
        });

        final List<Long> inputList = List.of(1L, 2L, 3L);
        final InputDevice id = new InputDevice(inputList);
        assertEquals(inputList, id.asList());

        assertEquals(Long.valueOf(1), id.get().get());
        assertEquals(Long.valueOf(2), id.get().get());
        assertEquals(Long.valueOf(3), id.get().get());

        assertEquals(List.of(), id.asList());
    }
}
