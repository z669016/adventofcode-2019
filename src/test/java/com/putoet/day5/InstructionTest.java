package com.putoet.day5;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static com.putoet.day7.ExceptionTester.ia;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class InstructionTest {
    @Test
    public void testMode() {
        assertEquals(InstructionMode.POSITION_MODE, InstructionMode.of(0));
        assertEquals(InstructionMode.IMMEDIATE_MODE, InstructionMode.of(1));

        IllegalArgumentException ia = ia(() -> InstructionMode.of(2));
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
        final Integer[] operants = new Integer[] {};

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip, address);
        verifyEmptyOutput(outputDevice);
    }

    private Instruction instruction;
    private Address ip;
    private Memory memory;
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    private Integer[] operants;

    private void setup(List<Integer> m, List<Integer> id) {
        instruction = InstructionFactory.of(m.get(0));
        ip = Address.START_ADDRESS;
        memory = Memory.of(m);
        inputDevice = new InputDevice(id);
        outputDevice = new OutputDevice();
        operants = m.subList(1, instruction.size()).toArray(new Integer[instruction.size() - 1]);
    }

    private void verifyAddress(Address ip, Optional<Address> address) {
        assertFalse(address.isEmpty());
        assertEquals(ip, address.get());
    }

    private void verifyEmptyOutput(OutputDevice outputDevice) {
        assertEquals(List.of(), outputDevice.dump());
    }

    private void verifyEmptyOutput() {
        verifyEmptyOutput(outputDevice);
    }

    private void verifyMemoryValue(Integer value, Integer address) {
        assertEquals(value, memory.peek(Address.of(address)));
    }

    @Test
    public void testSumImmediateInstruction() {
        setup(List.of(1101, 3, 5, 5, 99, 0), List.of());

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.SUM.size()), address);
        verifyMemoryValue(8, 5);
        verifyEmptyOutput();
    }

    @Test
    public void testSumPositionInstruction() {
        setup(List.of(1, 5, 6, 7, 99, 3, 5, 0), List.of());

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.SUM.size()), address);
        verifyMemoryValue(8, 7);
        verifyEmptyOutput();
    }

    @Test
    public void testProductImmediateInstruction() {
        setup(List.of(1102, 3, 5, 5, 99, 0), List.of());

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.PRODUCT.size()), address);
        verifyMemoryValue(15, 5);
        verifyEmptyOutput();
    }

    @Test
    public void testProductPositionInstruction() {
        setup(List.of(2, 5, 6, 7, 99, 3, 5, 0), List.of());

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.PRODUCT.size()), address);
        verifyMemoryValue(15, 7);
        verifyEmptyOutput();
    }

    @Test
    public void testInputInstruction() {
        setup(List.of(3, 3, 99, 0), List.of(7));

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.INPUT.size()), address);
        verifyMemoryValue(7, 3);
        verifyEmptyOutput();
    }

    @Test
    public void testOutputImmediateInstruction() {
        setup(List.of(104, 3, 99), List.of());

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.OUTPUT.size()), address);
        assertEquals(List.of(3), outputDevice.dump());
    }

    @Test
    public void testOutputPositionInstruction() {
        setup(List.of(4, 3, 99, 4, 7), List.of());

        final Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(ip.increase(Operation.OUTPUT.size()), address);
        assertEquals(List.of(4), outputDevice.dump());
    }

    @Test
    public void testJumpIfTrueImmediateInstruction() {
        setup(List.of(1105, 1, 6, 99, 99, 99, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(6), address);
        verifyEmptyOutput();

        setup(List.of(1105, 0, 6, 99, 99, 99, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(3), address);
        verifyEmptyOutput();
    }

    @Test
    public void testJumpIfTruePositionInstruction() {
        setup(List.of(5, 4, 5, 99, 1, 6, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(6), address);
        verifyEmptyOutput();

        setup(List.of(5, 4, 5, 99, 0, 6, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(3), address);
        verifyEmptyOutput();
    }

    @Test
    public void testJumpIfFalseImmediateInstruction() {
        setup(List.of(1106, 0, 6, 99, 99, 99, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(6), address);
        verifyEmptyOutput();

        setup(List.of(1106, 1, 6, 99, 99, 99, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(3), address);
        verifyEmptyOutput();
    }

    @Test
    public void testJumpIFalsePositionInstruction() {
        setup(List.of(6, 4, 5, 99, 0, 6, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(6), address);
        verifyEmptyOutput();

        setup(List.of(6, 4, 5, 99, 1, 6, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(3), address);
        verifyEmptyOutput();
    }

    @Test
    public void testLessThanImmediateInstruction() {
        setup(List.of(1107, 0, 1, 5, 99, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(1, 5);
        verifyEmptyOutput();

        setup(List.of(1107, 0, 0, 5, 99, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(0, 5);
        verifyEmptyOutput();
    }

    @Test
    public void testLessThanPositionInstruction() {
        setup(List.of(7, 5, 6, 7, 99, 0, 1, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(1, 7);
        verifyEmptyOutput();

        setup(List.of(7, 5, 6, 7, 99, 0, 0, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(0, 7);
        verifyEmptyOutput();
    }

    @Test
    public void testEqualImmediateInstruction() {
        setup(List.of(1108, 0, 0, 5, 99, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(1, 5);
        verifyEmptyOutput();

        setup(List.of(1108, 0, 1, 5, 99, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(0, 5);
        verifyEmptyOutput();
    }

    @Test
    public void testEqualPositionInstruction() {
        setup(List.of(8, 5, 6, 7, 99, 0, 0, 99), List.of());
        Optional<Address> address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(1, 7);
        verifyEmptyOutput();

        setup(List.of(8, 5, 6, 7, 99, 0, 1, 99), List.of());
        address = instruction.execute(ip, memory, inputDevice, outputDevice, operants);
        verifyAddress(Address.of(4), address);
        verifyMemoryValue(0, 7);
        verifyEmptyOutput();
    }
}
