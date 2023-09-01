package com.putoet.day11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurfaceTest {
    private Surface surface;

    @BeforeEach
    public void setup() {
        surface = new Surface();
    }

    @Test
    public void testEmptySurface() {
        assertEquals(0, surface.paintedPanelsCount());
        assertEquals("^", surface.toString());
    }

    @Test
    public void testPaintEmptySurface() {
        surface.paint(PanelColor.WHITE);
        assertEquals(1, surface.paintedPanelsCount());
        assertEquals("^", surface.toString());
    }

    @Test
    public void testExpandSurfaceLeft() {
        surface.paint(PanelColor.WHITE);
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals("<#", surface.toString());
    }

    @Test
    public void testExpandSurfaceRight() {
        surface.paint(PanelColor.WHITE);
        surface.turnRobotRight();
        surface.moveRobot();
        assertEquals("#>", surface.toString());
    }

    @Test
    public void testExpandSurfaceDown() {
        surface.paint(PanelColor.WHITE);
        surface.turnRobotRight();
        surface.turnRobotRight();
        surface.moveRobot();
        assertEquals("#\nv", surface.toString());
    }

    @Test
    public void testExpandSurfaceUp() {
        surface.paint(PanelColor.WHITE);
        surface.moveRobot();
        assertEquals("^\n#", surface.toString());
    }

    @Test
    public void testNewBlackSurface() {
        final var surface = new Surface(new String[]{".....", ".....", ".....", ".....", "^...."});
        System.out.println(surface);

        assertEquals(0, surface.paintedPanelsCount());
        assertEquals(".....\n.....\n.....\n.....\n^....", surface.toString());
    }

    @Test
    public void testNewColoredSurface() {
        final var surface = new Surface(new String[]{"#....", ".#...", "..^..", "...#.", "....#"});
        assertEquals(0, surface.paintedPanelsCount());
        assertEquals("#....\n.#...\n..^..\n...#.\n....#", surface.toString());
    }
}
