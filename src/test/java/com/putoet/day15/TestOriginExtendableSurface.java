package com.putoet.day15;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class TestOriginExtendableSurface {
    private Paintable wall = () -> '#';
    private Paintable open = () -> '.';
    private Paintable air = () -> 'A';

    private final IExtendableSurface surface = new OriginExtendableSurface();

    @Test
    public void testCount() {
        assertEquals(1, surface.count(Paintable.unnkown()));
        assertEquals(Paintable.unnkown(), surface.at(2,2));
        assertEquals(9, surface.count(Paintable.unnkown()));
    }

    @Test
    public void testAt() {
        assertEquals(Paintable.unnkown(), surface.at(-1,0));
        assertEquals(2, surface.count(Paintable.unnkown()));

        assertEquals(Paintable.unnkown(), surface.at(-10,-10));
        assertEquals(121, surface.count(Paintable.unnkown()));

        assertEquals(Paintable.unnkown(), surface.at(10,10));
        assertEquals(441, surface.count(Paintable.unnkown()));
    }

    @Test
    public void testAtExtendPainted() {
        final IExtendableSurface surface = new OriginExtendableSurface();
        defaultPaint(surface);

        assertEquals(open, surface.at(-1,3));
        assertEquals(9, surface.count(open));

        System.out.println(surface);
    }

    private void defaultPaint(IExtendableSurface surface) {
        surface.paint(-2, 0, wall);
        surface.paint(-1, 0, wall);
        surface.paint(0, 0, wall);
        surface.paint(1, 0, wall);
        surface.paint(2, 0, wall);

        surface.paint(-2, 1, wall);
        surface.paint(-1, 1, open);
        surface.paint(0, 1, open);
        surface.paint(1, 1, open);
        surface.paint(2, 1, wall);

        surface.paint(-2, 2, air);
        surface.paint(-1, 2, open);
        surface.paint(0, 2, open);
        surface.paint(1, 2, open);
        surface.paint(2, 2, wall);

        surface.paint(-2, 3, wall);
        surface.paint(-1, 3, open);
        surface.paint(0, 3, open);
        surface.paint(1, 3, open);
        surface.paint(2, 3, wall);

        surface.paint(-2, 4, wall);
        surface.paint(-1, 4, wall);
        surface.paint(0, 4, wall);
        surface.paint(1, 4, wall);
        surface.paint(2, 4, wall);
    }
}