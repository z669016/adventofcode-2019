package com.putoet.day10;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class LineOfSightMapTest {
    private Astroid origin = new Astroid(new Point(4, 2));
    private Astroid[] astroids = new Astroid[]{
            new Astroid(new Point(1, 0)),
            new Astroid(new Point(4, 0)),
            new Astroid(new Point(1, 2)),
            new Astroid(new Point(0, 2)),
            new Astroid(new Point(2, 2)),
            new Astroid(new Point(3, 2)),
            new Astroid(new Point(4, 3)),
            new Astroid(new Point(3, 4)),
            new Astroid(new Point(4, 4))
    };

    @Test
    public void testAdd() {
        final LineOfSightMap lineOfSightMap = new LineOfSightMap(origin);
        Arrays.stream(astroids).forEach(lineOfSightMap::add);

        System.out.println("In line of sight: " + lineOfSightMap.inLineOfSight());
        System.out.println("Hidden: " + lineOfSightMap.hidden());
        assertEquals(5, lineOfSightMap.inLineOfSightCount());

        System.out.println(lineOfSightMap.map());
    }
}