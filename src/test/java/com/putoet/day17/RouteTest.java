package com.putoet.day17;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {
    private final List<String> contains = List.of("A", "B", "C", "A", "D", "C", "A", "B", "C", "A", "B");
    private final List<String> first = List.of("A", "B", "C");
    private final List<String> second = List.of("A", "D");
    private final List<String> third = List.of("A", "B", "C", "D");
    private final List<String> fourth = List.of("A", "B");

    @Test
    void containsSubList() {
        assertTrue(Route.containsSubList(contains, 3, first));
        assertTrue(Route.containsSubList(contains, 0, second));
        assertFalse(Route.containsSubList(contains, 0, third));
        assertTrue(Route.containsSubList(contains, 0, fourth));
    }

    @Test
    void replaceSubList() {
        final List<String> list = new ArrayList<>(contains);
        assertEquals(List.of("1", "A", "D", "C", "1", "A", "B"), Route.replaceSubList(list, first, "1"));
    }

    @Test
    void route() {
        final Route route = new Route(List.of(
                "R","8","R","8","R","4","R","4","R","8","L","6","L","2",
                "R","4","R","4","R","8","R","8","R","8","L","6","L","2"));

        assertEquals("B,A,C,A,B,C", route.mainRoute());
        assertEquals("R,4,R,4,R,8", route.functionA());
        assertEquals("R,8,R,8", route.functionB());
        assertEquals("L,6,L,2", route.functionC());
    }
}