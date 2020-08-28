package com.putoet.day9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ResumableInputDeviceTest {
    @Test
    public void get() {
        final IInputDevice device = new ResumableInputDevice(List.of());

        Optional<Long> value = device.get();
        assertTrue(value.isEmpty());

        device.put(7L);
        value = device.get();
        assertFalse(value.isEmpty());
        assertEquals(Long.valueOf(7), value.get());
    }
}
