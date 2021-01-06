package com.putoet.day3;


import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteTest {

    @Test
    public void testRoute() {
        Route route = new Route();
        assertEquals("[]", route.toString());
        assertEquals(0, route.manhattenDistance());

        route = new Route(List.of("U1"));
        assertEquals("[(0, 1)]", route.toString());
        assertEquals(1, route.manhattenDistance());
        assertEquals(1, route.length());

        route = new Route(List.of("U1","R2"));
        assertEquals("[(0, 1), (1, 1), (2, 1)]", route.toString());
        assertEquals(3, route.manhattenDistance());
        assertEquals(3, route.length());

        route = new Route(List.of("U1","R2", "D1"));
        assertEquals("[(0, 1), (1, 1), (2, 1), (2, 0)]", route.toString());
        assertEquals(2, route.manhattenDistance());
        assertEquals(4, route.length());

        route = new Route(List.of("U1","R2", "D1", "L2"));
        assertEquals("[(0, 1), (1, 1), (2, 1), (2, 0), (1, 0), (0, 0)]", route.toString());
        assertEquals(0, route.manhattenDistance());
        assertEquals(6, route.length());

        route = new Route(List.of("U1", "R2", "D1", "L2"));
        assertEquals("[(0, 1), (1, 1), (2, 1), (2, 0), (1, 0), (0, 0)]", route.toString());

        assertEquals(4, route.stepsTo(Point.of(2, 0)));

        Route route1 = new Route(List.of("R8", "U5", "L5", "D3"));
        Route route2 = new Route(List.of("U7", "R6", "D4", "L4"));
        assertEquals(6, route1.intersect(route2).stream().min(Comparator.comparing(Point::manhattanDistance)).get().manhattanDistance());

        route1 = new Route(List.of("R75", "D30", "R83", "U83", "L12", "D49", "R71", "U7", "L72"));
        route2 = new Route(List.of("U62", "R66", "U55", "R34", "D71", "R55", "D58", "R83"));
        assertEquals(159, route1.intersect(route2).stream().min(Comparator.comparing(Point::manhattanDistance)).get().manhattanDistance());

        route1 = new Route(List.of("R98", "U47", "R26", "D63", "R33", "U87", "L62", "D20", "R33", "U53", "R51"));
        route2 = new Route(List.of("U98", "R91", "D20", "R16", "D67", "R40", "U7", "R15", "U6", "R7"));
        assertEquals(135, route1.intersect(route2).stream().min(Comparator.comparing(Point::manhattanDistance)).get().manhattanDistance());
    }
}