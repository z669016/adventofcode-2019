package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurfaceTest {
    @Test
    public void testEmptySurface() {
        final Surface surface = new Surface();
        assertEquals(0, surface.paintedPanelsCount());
        assertEquals("^", surface.toString());
    }

    @Test
    public void testPaintEmptySurface() {
        final Surface surface = new Surface();
        surface.paint(Color.WHITE);
        assertEquals(1, surface.paintedPanelsCount());
        assertEquals("^", surface.toString());
    }

    @Test
    public void testExpandSurfaceLeft() {
        final Surface surface = new Surface();
        surface.paint(Color.WHITE);
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals("<#", surface.toString());
    }

    @Test
    public void testExpandSurfaceRight() {
        final Surface surface = new Surface();
        surface.paint(Color.WHITE);
        surface.turnRobotRight();
        surface.moveRobot();
        assertEquals("#>", surface.toString());
    }

    @Test
    public void testExpandSurfaceDown() {
        final Surface surface = new Surface();
        surface.paint(Color.WHITE);
        surface.turnRobotRight();
        surface.turnRobotRight();
        surface.moveRobot();
        assertEquals("#\nv", surface.toString());
    }

    @Test
    public void testExpandSurfaceUp() {
        final Surface surface = new Surface();
        surface.paint(Color.WHITE);
        surface.moveRobot();
        assertEquals("^\n#", surface.toString());
    }

    @Test
    public void testNewBlackSurface() {
        final Surface surface = new Surface(new String[] {".....", ".....", ".....", ".....", "^...."});
        System.out.println(surface);

        assertEquals(0, surface.paintedPanelsCount());
        assertEquals(".....\n.....\n.....\n.....\n^....", surface.toString());
    }

    @Test
    public void testNewColoredSurface() {
        final Surface surface = new Surface(new String[] {"#....", ".#...", "..^..", "...#.", "....#"});
        assertEquals(4, surface.paintedPanelsCount());
        assertEquals("#....\n.#...\n..^..\n...#.\n....#", surface.toString());
    }
}
