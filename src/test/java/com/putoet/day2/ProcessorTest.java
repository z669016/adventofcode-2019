package com.putoet.day2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorTest {
    private Memory runOnProcessor(List<Integer> list) {
        final Memory memory = Memory.of(list);
        final Processor processor = new Processor(memory);
        processor.run();
        return processor.memory();
    }

    @Test
    public void testProcessor() {
        assertEquals("3500,9,10,70,2,3,11,0,99,30,40,50", runOnProcessor(List.of(1,9,10,3,2,3,11,0,99,30,40,50)).dump());
        assertEquals("2,0,0,0,99", runOnProcessor(List.of(1,0,0,0,99)).dump());
        assertEquals("2,3,0,6,99", runOnProcessor(List.of(2,3,0,3,99)).dump());
        assertEquals("2,4,4,5,99,9801", runOnProcessor(List.of(2,4,4,5,99,0)).dump());
        assertEquals("30,1,1,4,2,5,6,0,99", runOnProcessor(List.of(1,1,1,4,99,5,6,0,99)).dump());
    }
}
