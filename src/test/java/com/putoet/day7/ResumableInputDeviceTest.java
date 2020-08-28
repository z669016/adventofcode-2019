package com.putoet.day7;

import com.putoet.day5.IInputDevice;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ResumableInputDeviceTest {

    @Test
    public void get() {
        final IInputDevice device = new ResumableInputDevice(List.of());

        Optional<Integer> value = device.get();
        assertTrue(value.isEmpty());

        device.put(7);
        value = device.get();
        assertFalse(value.isEmpty());
        assertEquals(Integer.valueOf(7), value.get());
    }
}