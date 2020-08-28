package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PainterTest {
    @Test
    public void testPut00() {
        final Surface surface = mock(Surface.class);
        final Painter painter = new Painter(surface);

        painter.put(0L);
        painter.put(0l);

        verify(surface).paint(Color.BLACK);
        verify(surface).turnRobotLeft();
    }

    @Test
    public void testPut11() {
        final Surface surface = mock(Surface.class);
        final Painter painter = new Painter(surface);

        painter.put(1L);
        painter.put(1l);

        verify(surface).paint(Color.WHITE);
        verify(surface).turnRobotRight();
    }
}