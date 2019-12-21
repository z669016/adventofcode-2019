package com.putoet.day5;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class InstructionTest {
    @Test
    public void testMode() {
        assertEquals(InstructionMode.POSITION_MODE, InstructionMode.of(0));
        assertEquals(InstructionMode.IMMEDIATE_MODE, InstructionMode.of(1));

        IllegalArgumentException ia = null;
        try {
            InstructionMode.of(2);
            fail("Mode of 2 should not be accepted");
        } catch (IllegalArgumentException exc) {
            ia = exc;
        }
        assertNotNull(ia);
        assertEquals("Invalid mode '2'", ia.getMessage());
    }

    @Test
    public void testOperantModes() {
        SumInstruction instruction = (SumInstruction) InstructionFactory.of(1);
        assertEquals(InstructionMode.POSITION_MODE, instruction.instructionMode(0));
        assertEquals(InstructionMode.POSITION_MODE, instruction.instructionMode(1));

        instruction = (SumInstruction) InstructionFactory.of(101);
        assertEquals(Operation.SUM, instruction.operation());
        assertEquals(InstructionMode.IMMEDIATE_MODE, instruction.instructionMode(0));
        assertEquals(InstructionMode.POSITION_MODE, instruction.instructionMode(1));

        instruction = (SumInstruction) InstructionFactory.of(1001);
        assertEquals(Operation.SUM, instruction.operation());
        assertEquals(InstructionMode.POSITION_MODE, instruction.instructionMode(0));
        assertEquals(InstructionMode.IMMEDIATE_MODE, instruction.instructionMode(1));
    }

    @Test
    public void testExitInstruction() {
        final Instruction instruction = InstructionFactory.of(99);

        final Address ip = Address.START_ADDRESS;
        final Memory memory = Memory.of(List.of(99));
        final InputDevice inputDevice = new InputDevice(List.of());
        final OutputDevice outputDevice = new OutputDevice();
        final Integer operants[] = new Integer[] {};

        assertEquals(Operation.EXIT, instruction.operation());
        assertEquals(ip, instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());
    }

    private Instruction instruction;
    private Address ip;
    private Memory memory;
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    private Integer operants[];

    private void setup(List<Integer> m, List<Integer> id) {
        instruction = InstructionFactory.of(m.get(0));
        ip = Address.START_ADDRESS;
        memory = Memory.of(m);
        inputDevice = new InputDevice(id);
        outputDevice = new OutputDevice();
        operants = m.subList(1, instruction.size()).toArray(new Integer[instruction.size() - 1]);
    }

    @Test
    public void testSumImmediateInstruction() {
        setup(List.of(1101, 3, 5, 5, 99, 0), List.of());

        assertEquals(Operation.SUM, instruction.operation());
        assertEquals(ip.increase(Operation.SUM.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(8), memory.peek(Address.of(5)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testSumPositionInstruction() {
        setup(List.of(1, 5, 6, 7, 99, 3, 5, 0), List.of());

        assertEquals(Operation.SUM, instruction.operation());
        assertEquals(ip.increase(Operation.SUM.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(8), memory.peek(Address.of(7)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testProductImmediateInstruction() {
        setup(List.of(1102, 3, 5, 5, 99, 0), List.of());

        assertEquals(Operation.PRODUCT, instruction.operation());
        assertEquals(ip.increase(Operation.PRODUCT.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(15), memory.peek(Address.of(5)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testProductPositionInstruction() {
        setup(List.of(2, 5, 6, 7, 99, 3, 5, 0), List.of());

        assertEquals(Operation.PRODUCT, instruction.operation());
        assertEquals(ip.increase(Operation.PRODUCT.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(15), memory.peek(Address.of(7)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testInputInstruction() {
        setup(List.of(3, 3, 99, 0), List.of(7));

        assertEquals(Operation.INPUT, instruction.operation());
        assertEquals(ip.increase(Operation.INPUT.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(7), memory.peek(Address.of(3)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testOutputImmediateInstruction() {
        setup(List.of(104, 3, 99), List.of());

        assertEquals(Operation.OUTPUT, instruction.operation());
        assertEquals(ip.increase(Operation.OUTPUT.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[3]", outputDevice.toString());
    }

    @Test
    public void testOutputPositionInstruction() {
        setup(List.of(4, 3, 99, 4, 7), List.of());

        assertEquals(Operation.OUTPUT, instruction.operation());
        assertEquals(ip.increase(Operation.OUTPUT.size()), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[4]", outputDevice.toString());
    }

    @Test
    public void testJumpIfTrueImmediateInstruction() {
        setup(List.of(1105, 1, 6, 99, 99, 99, 99), List.of());
        assertEquals(Operation.JUMP_IF_TRUE, instruction.operation());
        assertEquals(Address.of(6), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(1105, 0, 6, 99, 99, 99, 99), List.of());
        assertEquals(Operation.JUMP_IF_TRUE, instruction.operation());
        assertEquals(Address.of(3), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testJumpIfTruePositionInstruction() {
        setup(List.of(5, 4, 5, 99, 1, 6, 99), List.of());
        assertEquals(Operation.JUMP_IF_TRUE, instruction.operation());
        assertEquals(Address.of(6), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(5, 4, 5, 99, 0, 6, 99), List.of());
        assertEquals(Operation.JUMP_IF_TRUE, instruction.operation());
        assertEquals(Address.of(3), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testJumpIfFalseImmediateInstruction() {
        setup(List.of(1106, 0, 6, 99, 99, 99, 99), List.of());
        assertEquals(Operation.JUMP_IF_FALSE, instruction.operation());
        assertEquals(Address.of(6), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(1106, 1, 6, 99, 99, 99, 99), List.of());
        assertEquals(Operation.JUMP_IF_FALSE, instruction.operation());
        assertEquals(Address.of(3), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testJumpIFalsePositionInstruction() {
        setup(List.of(6, 4, 5, 99, 0, 6, 99), List.of());
        assertEquals(Operation.JUMP_IF_FALSE, instruction.operation());
        assertEquals(Address.of(6), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(6, 4, 5, 99, 1, 6, 99), List.of());
        assertEquals(Operation.JUMP_IF_FALSE, instruction.operation());
        assertEquals(Address.of(3), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testLessThanImmediateInstruction() {
        setup(List.of(1107, 0, 1, 5, 99, 99), List.of());
        assertEquals(Operation.LESS_THAN, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(1), memory.peek(Address.of(5)));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(1107, 0, 0, 5, 99, 99), List.of());
        assertEquals(Operation.LESS_THAN, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(0), memory.peek(Address.of(5)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testLessThanPositionInstruction() {
        setup(List.of(7, 5, 6, 7, 99, 0, 1, 99), List.of());
        assertEquals(Operation.LESS_THAN, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(1), memory.peek(Address.of(7)));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(7, 5, 6, 7, 99, 0, 0, 99), List.of());
        assertEquals(Operation.LESS_THAN, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(0), memory.peek(Address.of(7)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testEqualImmediateInstruction() {
        setup(List.of(1108, 0, 0, 5, 99, 99), List.of());
        assertEquals(Operation.EQUAL, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(1), memory.peek(Address.of(5)));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(1108, 0, 1, 5, 99, 99), List.of());
        assertEquals(Operation.EQUAL, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(0), memory.peek(Address.of(5)));
        assertEquals("[]", outputDevice.toString());
    }

    @Test
    public void testEqualPositionInstruction() {
        setup(List.of(8, 5, 6, 7, 99, 0, 0, 99), List.of());
        assertEquals(Operation.EQUAL, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(1), memory.peek(Address.of(7)));
        assertEquals("[]", outputDevice.toString());

        setup(List.of(8, 5, 6, 7, 99, 0, 1, 99), List.of());
        assertEquals(Operation.EQUAL, instruction.operation());
        assertEquals(Address.of(4), instruction.execute(ip, memory, inputDevice, outputDevice, operants));
        assertEquals(Integer.valueOf(0), memory.peek(Address.of(7)));
        assertEquals("[]", outputDevice.toString());
    }
}
