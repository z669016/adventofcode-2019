package com.putoet.day15;

import com.putoet.day9.IInputDevice;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestExplorer {

    @Test
    public void testExplore() {
        final IExtendableSurface surface = new OriginExtendableSurface();
        final Sensor sensor = mock(Sensor.class);
        final IInputDevice inputDevice = mock(IInputDevice.class);
        final Navigator navigator = new Navigator(inputDevice, new ArrayList<Direction>());
        final Explorer explorer = new Explorer(surface, navigator, sensor);

        when(sensor.read()).thenReturn(Sensor.State.HIT_WALL);
        explorer.explore();
        assertEquals(Point.ORIGIN, navigator.currentPoint());
        assertEquals(Tile.Type.WALL, navigator.currentTile().get(Direction.NORTH).get().type());
    }
}