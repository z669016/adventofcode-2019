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
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<>());

        navigator.north();
        verify(inputDevice).put(Navigator.NORTH);
        assertEquals(Point.of(0, 1), navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().south().north());

        navigator.south();
        verify(inputDevice).put(Navigator.SOUTH);
        assertEquals(Point.ORIGIN, navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().north().south());

        navigator.west();
        verify(inputDevice).put(Navigator.WEST);
        assertEquals(Point.of(1, 0), navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().east().west());

        navigator.east();
        verify(inputDevice).put(Navigator.EAST);
        assertEquals(Point.ORIGIN, navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().west().east());
    }

    @Test
    public void testTrace() {
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<>());

        navigator.north();
        navigator.south();
        navigator.west();
        navigator.east();
        assertEquals(List.of(Navigator.NORTH, Navigator.SOUTH, Navigator.WEST, Navigator.EAST), navigator.trace());
    }

    @Test
    public void testBack() {
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<>());

        navigator.north();
        navigator.south();
        navigator.west();
        navigator.east();
        navigator.back();
        assertEquals(Point.of(1, 0), navigator.currentPoint());
        assertEquals(List.of(Navigator.NORTH, Navigator.SOUTH, Navigator.WEST), navigator.trace());
    }
}
