package com.putoet.day5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

public class ProcessorTest {
    private Memory runOnProcessor(List<Integer> list) {
        final Memory memory = Memory.of(list);
        final InputDevice inputDevice = new InputDevice(List.of());
        final OutputDevice outputDevice = new OutputDevice();
        final Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();
        return processor.memory();
    }

    private OutputDevice runOnProcessor(List<Integer> list, List<Integer> id) {
        final Memory memory = Memory.of(list);
        final InputDevice inputDevice = new InputDevice(id);
        final OutputDevice outputDevice = new OutputDevice();
        final Processor processor = new Processor(memory, inputDevice, outputDevice);
        processor.run();

        return processor.outputDevice();
    }

    @Test
    public void testProcessor() {
        assertEquals("3500,9,10,70,2,3,11,0,99,30,40,50", runOnProcessor(List.of(1,9,10,3,2,3,11,0,99,30,40,50)).dump());
        assertEquals("2,0,0,0,99", runOnProcessor(List.of(1,0,0,0,99)).dump());
        assertEquals("2,3,0,6,99", runOnProcessor(List.of(2,3,0,3,99)).dump());
        assertEquals("2,4,4,5,99,9801", runOnProcessor(List.of(2,4,4,5,99,0)).dump());
        assertEquals("30,1,1,4,2,5,6,0,99", runOnProcessor(List.of(1,1,1,4,99,5,6,0,99)).dump());
    }

    @Test
    public void testDay5() {
        final List<Integer> caseOne = List.of(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9);
        assertEquals("[0]", runOnProcessor(caseOne, List.of(0)).toString());
        assertEquals("[1]", runOnProcessor(caseOne, List.of(13)).toString());

        final List<Integer> caseTwo = List.of(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1);
        assertEquals("[0]", runOnProcessor(caseTwo, List.of(0)).toString());
        assertEquals("[1]", runOnProcessor(caseTwo, List.of(13)).toString());

        final List<Integer> caseThree = List.of(
                3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99);
        assertEquals("[999]", runOnProcessor(caseThree, List.of(7)).toString());
        assertEquals("[1000]", runOnProcessor(caseThree, List.of(8)).toString());
        assertEquals("[1001]", runOnProcessor(caseThree, List.of(9)).toString());
    }
}
