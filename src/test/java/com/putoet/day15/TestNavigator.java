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

        navigator.move(Direction.NORTH);
        verify(inputDevice).put(1L);
        assertEquals(Point.of(0, 1), navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().get(Direction.SOUTH).get().get(Direction.NORTH).get());

        navigator.move(Direction.SOUTH);
        verify(inputDevice).put(2L);
        assertEquals(Point.ORIGIN, navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().get(Direction.NORTH).get().get(Direction.SOUTH).get());

        navigator.move(Direction.WEST);
        verify(inputDevice).put(3L);
        assertEquals(Point.of(1, 0), navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().get(Direction.EAST).get().get(Direction.WEST).get());

        navigator.move(Direction.EAST);
        verify(inputDevice).put(4L);
        assertEquals(Point.ORIGIN, navigator.currentPoint());
        assertEquals(navigator.currentTile(), navigator.currentTile().get(Direction.WEST).get().get(Direction.EAST).get());
    }

    @Test
    public void testTrace() {
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<>());

        navigator.move(Direction.NORTH);
        navigator.move(Direction.SOUTH);
        navigator.move(Direction.WEST);
        navigator.move(Direction.EAST);
        assertEquals(List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST), navigator.trace());
    }

    @Test
    public void testBack() {
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<>());

        navigator.move(Direction.NORTH);
        navigator.move(Direction.SOUTH);
        navigator.move(Direction.WEST);
        navigator.move(Direction.EAST);
        navigator.back();
        assertEquals(Point.of(1, 0), navigator.currentPoint());
        assertEquals(List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST), navigator.trace());
    }
}
