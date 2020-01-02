package com.putoet.day11;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

public class CameraTest {
    @Test
    public void cameraTest() {
        final Surface surface = new Surface(new String[] {"#....", ".#...", "..^..", "...#.", "....#"});
        final Camera camera = new Camera(surface);

        surface.moveRobot();
        assertEquals(Color.BLACK, camera.color());
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals(Color.WHITE, camera.color());
        surface.moveRobot();
        assertEquals(Color.BLACK, camera.color());
    }

    @Test
    public void testGet() {
        final Surface surface = new Surface(new String[] {"#....", ".#...", "..^..", "...#.", "....#"});
        final Camera camera = new Camera(surface);

        surface.moveRobot();
        assertEquals(Optional.of(0L), camera.get());
        surface.turnRobotLeft();
        surface.moveRobot();
        assertEquals(Optional.of(1L), camera.get());
        surface.moveRobot();
        assertEquals(Optional.of(0L), camera.get());
    }
}
