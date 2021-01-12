package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraTest {
    @Test
    public void cameraTest() {
        final Surface surface = new Surface(new String[]{"#....", ".#...", "..^..", "...#.", "....#"});
        final Camera camera = new Camera(surface);

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
        final Surface surface = new Surface(new String[]{"#....", ".#...", "..^..", "...#.", "....#"});
        final Camera camera = new Camera(surface);

        surface.moveRobot();
        assertEquals(0, camera.poll().getAsLong());
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals(1, camera.poll().getAsLong());
        surface.moveRobot();
        assertEquals(0, camera.poll().getAsLong());
    }
}
