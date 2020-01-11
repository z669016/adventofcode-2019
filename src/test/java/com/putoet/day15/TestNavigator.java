package com.putoet.day15;

import com.putoet.day9.IInputDevice;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestNavigator {
    @Test
    public void testInputDevice() {
        final ExtendableSurface surface = new ExtendableSurface();
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(surface, inputDevice, new ArrayList<>());

        navigator.north();
        verify(inputDevice).put(Navigator.NORTH);

        navigator.south();
        verify(inputDevice).put(Navigator.SOUTH);

        navigator.west();
        verify(inputDevice).put(Navigator.WEST);

        navigator.east();
        verify(inputDevice).put(Navigator.EAST);

        System.out.println(surface);
    }

    @Test
    public void testTrace() {
        final ExtendableSurface surface = new ExtendableSurface();
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(surface, inputDevice, new ArrayList<>());

        navigator.north();
        navigator.south();
        navigator.west();
        navigator.east();
        assertEquals(List.of(Navigator.NORTH, Navigator.SOUTH, Navigator.WEST, Navigator.EAST), navigator.trace());
    }

    @Test
    public void testBack() {
        final ExtendableSurface surface = new ExtendableSurface();
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(surface, inputDevice, new ArrayList<>());

        navigator.north();
        navigator.south();
        navigator.west();
        navigator.east();
        navigator.back();
        navigator.back();
        assertEquals(List.of(Navigator.NORTH, Navigator.SOUTH), navigator.trace());
    }
}
