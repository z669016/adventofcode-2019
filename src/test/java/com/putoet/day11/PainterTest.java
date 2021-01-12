package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PainterTest {
    @Test
    public void testPut00() {
        final Surface surface = mock(Surface.class);
        final Painter painter = new Painter(surface);

        painter.offer(0);
        painter.offer(0);

        verify(surface).paint(PanelColor.BLACK);
        verify(surface).turnRobotLeft();
    }

    @Test
    public void testPut11() {
        final Surface surface = mock(Surface.class);
        final Painter painter = new Painter(surface);

        painter.offer(1);
        painter.offer(1);

        verify(surface).paint(PanelColor.WHITE);
        verify(surface).turnRobotRight();
    }
}