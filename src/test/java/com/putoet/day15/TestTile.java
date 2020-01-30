package com.putoet.day15;

import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;
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
        final Tile newTile = start.move(Direction.NORTH);
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertTrue(start.get(Direction.NORTH).isPresent());
        assertEquals(start.get(Direction.NORTH).get(), newTile);
        assertFalse(start.get(Direction.WEST).isPresent());
        assertFalse(start.get(Direction.SOUTH).isPresent());
        assertFalse(start.get(Direction.EAST).isPresent());

        assertTrue(newTile.get(Direction.SOUTH).isPresent());
        assertEquals(newTile.get(Direction.SOUTH).get(), start);
        assertFalse(newTile.get(Direction.NORTH).isPresent());
        assertFalse(newTile.get(Direction.WEST).isPresent());
        assertFalse(newTile.get(Direction.EAST).isPresent());
    }

    @Test
    public void testGoWest() {
        final Tile start = Tile.START;
        final Tile newTile = start.move(Direction.WEST);
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertTrue(start.get(Direction.WEST).isPresent());
        assertEquals(start.get(Direction.WEST).get(), newTile);
        assertFalse(start.get(Direction.NORTH).isPresent());
        assertFalse(start.get(Direction.SOUTH).isPresent());
        assertFalse(start.get(Direction.EAST).isPresent());

        assertTrue(newTile.get(Direction.EAST).isPresent());
        assertEquals(newTile.get(Direction.EAST).get(), start);
        assertFalse(newTile.get(Direction.NORTH).isPresent());
        assertFalse(newTile.get(Direction.WEST).isPresent());
        assertFalse(newTile.get(Direction.SOUTH).isPresent());
    }

    @Test
    public void testGoSouth() {
        final Tile start = Tile.START;
        final Tile newTile = start.move(Direction.SOUTH);
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertTrue(start.get(Direction.SOUTH).isPresent());
        assertEquals(start.get(Direction.SOUTH).get(), newTile);
        assertFalse(start.get(Direction.NORTH).isPresent());
        assertFalse(start.get(Direction.WEST).isPresent());
        assertFalse(start.get(Direction.EAST).isPresent());

        assertTrue(newTile.get(Direction.NORTH).isPresent());
        assertEquals(newTile.get(Direction.NORTH).get(), start);
        assertFalse(newTile.get(Direction.WEST).isPresent());
        assertFalse(newTile.get(Direction.SOUTH).isPresent());
        assertFalse(newTile.get(Direction.EAST).isPresent());
    }

    @Test
    public void testGoEast() {
        final Tile start = Tile.START;
        final Tile newTile = start.move(Direction.EAST);
        assertEquals(newTile.type(), Tile.Type.UNKNOWN);

        assertTrue(start.get(Direction.EAST).isPresent());
        assertEquals(start.get(Direction.EAST).get(), newTile);
        assertFalse(start.get(Direction.NORTH).isPresent());
        assertFalse(start.get(Direction.WEST).isPresent());
        assertFalse(start.get(Direction.SOUTH).isPresent());

        assertTrue(newTile.get(Direction.WEST).isPresent());
        assertEquals(newTile.get(Direction.WEST).get(), start);
        assertFalse(newTile.get(Direction.NORTH).isPresent());
        assertFalse(newTile.get(Direction.SOUTH).isPresent());
        assertFalse(newTile.get(Direction.EAST).isPresent());
    }

    @Test
    public void testIsDeadEnd() {
        final Tile start = Tile.START;
        start.move(Direction.NORTH).discovered(Tile.Type.WALL);
        start.move(Direction.EAST).discovered(Tile.Type.BLOCKED);
        start.move(Direction.SOUTH).discovered(Tile.Type.WALL);

        assertTrue(start.isDeadEnd());
    }
}