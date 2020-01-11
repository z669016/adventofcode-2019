package com.putoet.day15;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestExtendableSurface {
    private Paintable wall = () -> '#';
    private Paintable open = () -> '.';
    private Paintable air = () -> 'A';

    @Test
    public void testSurface() {
        final ExtendableSurface surface = new ExtendableSurface();
        defaultPaint(surface);

        final String expectedScreen =
                "###\n" +
                "#.#\n" +
                "#.A\n" +
                "#.#\n" +
                "###\n";
                assertEquals(expectedScreen, surface.toString());
    }

    @Test
    public void testAt() {
        final ExtendableSurface surface = new ExtendableSurface();
        assertEquals(Paintable.unnkown(), surface.at(Point.of(0,0)));
        assertEquals(1, surface.count(Paintable.unnkown()));
    }

    @Test
    public void testAtExtend() {
        final ExtendableSurface surface = new ExtendableSurface();
        assertEquals(Paintable.unnkown(), surface.at(Point.of(2,2)));
        assertEquals(9, surface.count(Paintable.unnkown()));
    }

    @Test
    public void testAtExtendPainted() {
        final ExtendableSurface surface = new ExtendableSurface();
        defaultPaint(surface);

        assertEquals(open, surface.at(Point.of(1,3)));
        assertEquals(3, surface.count(open));
    }

    private void defaultPaint(ExtendableSurface surface) {
        surface.paint(Point.of(0, 0), wall);
        surface.paint(Point.of(1, 0), wall);
        surface.paint(Point.of(2, 0), wall);
        surface.paint(Point.of(0, 1), wall);
        surface.paint(Point.of(1, 1), open);
        surface.paint(Point.of(2, 1), wall);
        surface.paint(Point.of(0, 2), wall);
        surface.paint(Point.of(1, 2), open);
        surface.paint(Point.of(2, 2), air);
        surface.paint(Point.of(0, 3), wall);
        surface.paint(Point.of(1, 3), open);
        surface.paint(Point.of(2, 3), wall);
        surface.paint(Point.of(0, 4), wall);
        surface.paint(Point.of(1, 4), wall);
        surface.paint(Point.of(2, 4), wall);
    }
}
