//package com.putoet.day13;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TestSurface {
//    @Test
//    public void testSurface() {
//        final List<Long> list = List.of(1L, 2L, 3L, 6L, 5L, 4L);
//        final Screen screen = new Screen();
//
//        list.forEach(screen::put);
//
//        System.out.println(screen);
//
//        final String expectedScreen =
//                "SCORE 0\n" +
//                        "       \n" +
//                        "       \n" +
//                        " o     \n" +
//                        "       \n" +
//                        "       \n" +
//                        "      -\n";
//        assertEquals(expectedScreen, screen.toString());
//    }
//}
