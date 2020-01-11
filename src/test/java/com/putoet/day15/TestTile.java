package com.putoet.day15;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static com.putoet.day7.ExceptionTester.is;
import static org.junit.Assert.*;

public class TestTile {
    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field north = Tile.class.getDeclaredField("north");
        north.setAccessible(true);
        north.set(Tile.START, null);

        Field west = Tile.class.getDeclaredField("west");
        west.setAccessible(true);
        west.set(Tile.START, null);

        Field south = Tile.class.getDeclaredField("south");
        south.setAccessible(true);
        south.set(Tile.START, null);

        Field east = Tile.class.getDeclaredField("east");
        east.setAccessible(true);
        east.set(Tile.START, null);
    }

    @Test
    public void testStartPaint() {
        final Tile tile = Tile.START;
        assertEquals('.', tile.paint());
    }

    @Test
    public void testReDiscover() {
        final Tile tile = Tile.START;
        IllegalStateException is = is(() -> {tile.discovered(Tile.Type.UNKNOWN); return 1;} );
        assertNotNull(is);
        assertEquals("Tile was already discovered ('.')", is.getMessage());
    }

    @Test
    public void testGoNorth() {
        final Tile start = Tile.START;
        final Tile newTile = start.goNorth();
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertEquals(start.north(), newTile);
        assertNull(start.west());
        assertNull(start.south());
        assertNull(start.east());

        assertEquals(newTile.south(), start);
        assertNull(newTile.north());
        assertNull(newTile.west());
        assertNull(newTile.east());
    }

    @Test
    public void testGoWest() {
        final Tile start = Tile.START;
        final Tile newTile = start.goWest();
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertEquals(start.west(), newTile);
        assertNull(start.north());
        assertNull(start.south());
        assertNull(start.east());

        assertEquals(newTile.east(), start);
        assertNull(newTile.north());
        assertNull(newTile.west());
        assertNull(newTile.south());
    }

    @Test
    public void testGoSouth() {
        final Tile start = Tile.START;
        final Tile newTile = start.goSouth();
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertEquals(start.south(), newTile);
        assertNull(start.north());
        assertNull(start.west());
        assertNull(start.east());

        assertEquals(newTile.north(), start);
        assertNull(newTile.west());
        assertNull(newTile.south());
        assertNull(newTile.east());
    }

    @Test
    public void testGoEast() {
        final Tile start = Tile.START;
        final Tile newTile = start.goEast();
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertEquals(start.east(), newTile);
        assertNull(start.north());
        assertNull(start.west());
        assertNull(start.south());

        assertEquals(newTile.west(), start);
        assertNull(newTile.north());
        assertNull(newTile.south());
        assertNull(newTile.east());
    }
}