package com.putoet.day15;

import com.putoet.day9.IInputDevice;
import com.putoet.day9.IOutputDevice;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestExplorer {

    @Test
    public void testExplore() {
        final IExtendableSurface surface = new OriginExtendableSurface();
        final IOutputDevice outputDevice = mock(IOutputDevice.class);
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<Direction>());
        final Explorer explorer = new Explorer(surface, navigator, outputDevice);

        when(outputDevice.get()).thenReturn((long) Explorer.BLOCKED);
        explorer.explore();
        assertEquals(Point.ORIGIN, navigator.currentPoint());
        assertEquals(Tile.Type.WALL, navigator.currentTile().north().type());
    }
}