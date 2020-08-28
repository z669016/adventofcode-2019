package com.putoet.day8;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LayerTest {

    @Test
    public void countTest() {
        final Layer layer = Layer.of(Dimension.of(3, 2), List.of(1, 2, 1, 2, 3, 2));
        assertEquals(2, layer.count(1));
        assertEquals(3, layer.count(2));
        assertEquals(1, layer.count(3));
    }
}