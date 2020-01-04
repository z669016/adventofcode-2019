package com.putoet.day13;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestSurface {
    @Test
    public void testSurface() {
        final List<Long> list = List.of(1L,2L,3L,6L,5L,4L);
        final Screen screen = new Screen();

        list.forEach(screen::put);

        System.out.println(screen);

        final String expectedScreen =
                "       \n" +
                "⚽      \n" +
                "       \n" +
                "       \n" +
                "     \uD83C\uDFD3 \n" +
                "       \n";
        assertEquals(expectedScreen, screen.toString());
    }
}
