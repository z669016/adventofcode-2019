package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraTest {
    @Test
    public void cameraTest() {
        final var surface = new Surface(new String[]{"#....", ".#...", "..^..", "...#.", "....#"});
        final var camera = new Camera(surface);

        surface.moveRobot();
        assertEquals(PanelColor.BLACK, camera.color());
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals(PanelColor.WHITE, camera.color());
        surface.moveRobot();
        assertEquals(PanelColor.BLACK, camera.color());
    }

    @Test
    public void testGet() {
        final var surface = new Surface(new String[]{"#....", ".#...", "..^..", "...#.", "....#"});
        final var camera = new Camera(surface);

        surface.moveRobot();
        assertEquals(0, camera.poll().orElseThrow());
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals(1, camera.poll().orElseThrow());
        surface.moveRobot();
        assertEquals(0, camera.poll().orElseThrow());
    }
}
