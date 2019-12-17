package com.putoet.day2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

public class Day2Test {
    @Test
    public void testCreateOperation() {
        assertEquals(Operation.SUM, Operation.of(1));
        assertEquals(Operation.PRODUCT, Operation.of(2));
        assertEquals(Operation.EXIT, Operation.of(99));

        try {
            Operation.of(-1);
        } catch (IllegalArgumentException exc) {
            assertEquals("Illegal opcode -1", exc.getMessage());
        }
    }

    @Test
    public void testOperationFunction() {
        final BiFunction<Integer, Integer, Integer> sum = Operation.SUM.biFunction();
        assertEquals(sum.apply(Integer.valueOf(1), Integer.valueOf(2)), Integer.valueOf(3));

        final BiFunction<Integer, Integer, Integer> product = Operation.PRODUCT.biFunction();
        assertEquals(product.apply(Integer.valueOf(2), Integer.valueOf(2)), Integer.valueOf(4));

        try {
            Operation.EXIT.biFunction();
        } catch (IllegalArgumentException exc) {
            assertEquals("NOP", exc.getMessage());
        }
    }

    @Test
    public void testOperationOperants() {
        assertEquals(1, Operation.EXIT.size());
        assertEquals(4, Operation.PRODUCT.size());
        assertEquals(4, Operation.SUM.size());
    }

    @Test
    public void testCreateAddress() {
        assertEquals(Address.of(0).toInt(), 0);
        assertEquals(Address.of(99).toInt(), 99);

        try {
            Address.of(-2);
        } catch (IllegalArgumentException exc) {
            assertEquals("Address cannot be negative -2", exc.getMessage());
        }
    }

    @Test
    public void testAccessAddress() {
        assertEquals(Address.START_ADDRESS.increase(5).toInt(), Address.of(5).toInt());
    }

    @Test
    public void testCreateMemory() {
        List<Integer> integerList = List.of(1,9,10,3,2,3,11,0,99,30,40,50);
        Memory memory = Memory.of(integerList);
        assertEquals(memory.size(), integerList.size());
        assertEquals("1,9,10,3,2,3,11,0,99,30,40,50", memory.dump());

        try {
            Memory.of(null);
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }

        try {
            Memory.of(Collections.emptyList());
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }
    }

    @Test
    public void testAccessMemory() {
        Memory memory = Memory.of(List.of(1,9,10,3,2,3,11,0,99,30,40,50));

        assertEquals(memory.peek(Address.of(0)), Integer.valueOf(1));
        assertEquals(memory.peek(Address.of(11)), Integer.valueOf(50));

        memory.poke(Address.of(9), 31);
        assertEquals(memory.peek(Address.of(9)), Integer.valueOf(31));

        try {
            memory.peek(Address.of(12));
        } catch (IllegalArgumentException exc) {
            assertEquals("Invalid memory address 12", exc.getMessage());
        }

        try {
            Memory.of(null);
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }

        try {
            Memory.of(new ArrayList<Integer>());
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }
    }

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
