package com.putoet.day3;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class Day3Test {
    @Test
    public void testCoordinate() {
        assertEquals(Coordinate.of(0, 1), Coordinate.ORIGIN.move(Direction.UP));
        assertEquals(Coordinate.of(0, -1), Coordinate.ORIGIN.move(Direction.DOWN));
        assertEquals(Coordinate.of(-1, 0), Coordinate.ORIGIN.move(Direction.LEFT));
        assertEquals(Coordinate.of(1, 0), Coordinate.ORIGIN.move(Direction.RIGHT));
    }

    @Test
    public void testCoordinateManhattanDistance() {
        Coordinate c1 = Coordinate.of(0, 0);
        assertEquals(0, c1.manhattanDistance());

        c1 = Coordinate.of(-1, -3);
        assertEquals(4, c1.manhattanDistance());

        c1 = Coordinate.of(3, 7);
        assertEquals(10, c1.manhattanDistance());
    }

    @Test
    public void testDirection() {
        assertEquals(Direction.UP, Direction.of('U'));
        assertEquals(Direction.DOWN, Direction.of('D'));
        assertEquals(Direction.LEFT, Direction.of('L'));
        assertEquals(Direction.RIGHT, Direction.of('R'));

        try {
            Direction.of(null);
        } catch (IllegalArgumentException exc) {
            assertEquals("Not a direction", exc.getMessage());
        }

        try {
            Direction.of('q');
        } catch (IllegalArgumentException exc) {
            assertEquals("Not a direction: q", exc.getMessage());
        }
    }

    @Test
    public void testPath() {
        RuntimeException re = null;
        try {
            String s = null;
            Path.of(s);
        } catch (IllegalArgumentException exc) {
            re = exc;
        }
        assertNotNull(re);
        assertEquals("No path", re.getMessage());

        re = null;
        try {
            Path.of("q");
        } catch (IllegalArgumentException exc) {
            re = exc;
        }
        assertNotNull(re);
        assertEquals("Not a direction: Q", re.getMessage());

        re = null;
        try {
            Path.of("uu");
        } catch (IllegalArgumentException exc) {
            re = exc;
        }
        assertNotNull(re);
        assertEquals("Invalid path: uu", re.getMessage());

        re = null;
        try {
            Path.of("u-2");
        } catch (IllegalArgumentException exc) {
            re = exc;
        }
        assertNotNull(re);
        assertEquals("Invalid length on path: u-2", re.getMessage());

        Path path = Path.of("D");
        assertEquals(Direction.DOWN, path.direction());
        assertEquals(0, path.length());

        path = Path.of("U20");
        assertEquals(Direction.UP, path.direction());
        assertEquals(20, path.length());

//        List<Path> paths = Path.of("U1", "r1", "d1", "l1");
//        assertEquals(paths, List.of(Path.of("u1"), Path.of("r1"), Path.of("d1"), Path.of("l1")));
    }

    @Test
    public void testRoute() {
        Route route = new Route();
        assertEquals("[]", route.toString());
        assertEquals(0, route.distanceToOrigin());

        route.add(Path.of("U1"));
        assertEquals("[(0,1)]", route.toString());
        assertEquals(1, route.distanceToOrigin());
        assertEquals(1, route.length());

        route.add(Path.of("R2"));
        assertEquals("[(0,1), (1,1), (2,1)]", route.toString());
        assertEquals(3, route.distanceToOrigin());
        assertEquals(3, route.length());

        route.add(Path.of("D1"));
        assertEquals("[(0,1), (1,1), (2,1), (2,0)]", route.toString());
        assertEquals(2, route.distanceToOrigin());
        assertEquals(4, route.length());

        route.add(Path.of("L2"));
        assertEquals("[(0,1), (1,1), (2,1), (2,0), (1,0), (0,0)]", route.toString());
        assertEquals(0, route.distanceToOrigin());
        assertEquals(6, route.length());

        route = new Route();
        route.add(Path.of("U1", "R2", "D1", "L2"));
        assertEquals("[(0,1), (1,1), (2,1), (2,0), (1,0), (0,0)]", route.toString());

        route = new Route("U1,R2, D1, L2 ");
        assertEquals("[(0,1), (1,1), (2,1), (2,0), (1,0), (0,0)]", route.toString());

        assertEquals(4, route.stepsTo(Coordinate.of(2, 0)));

        Route route1 = new Route("R8,U5,L5,D3");
        Route route2 = new Route("U7,R6,D4,L4");
        assertEquals(6, route1.intersect(route2).stream().min(Comparator.comparing(Coordinate::manhattanDistance)).get().manhattanDistance());

        route1 = new Route("R75,D30,R83,U83,L12,D49,R71,U7,L72");
        route2 = new Route("U62,R66,U55,R34,D71,R55,D58,R83");
        assertEquals(159, route1.intersect(route2).stream().min(Comparator.comparing(Coordinate::manhattanDistance)).get().manhattanDistance());

        route1 = new Route("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51");
        route2 = new Route("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");
        assertEquals(135, route1.intersect(route2).stream().min(Comparator.comparing(Coordinate::manhattanDistance)).get().manhattanDistance());
    }
}