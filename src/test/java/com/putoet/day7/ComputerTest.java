package com.putoet.day7;

import com.putoet.day5.Processor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ComputerTest {
    @Test
    public void testComputer() {
        final List<Integer> memory = List.of(3, 5, 4, 5, 99, 0);
        final List<Integer> inputDevice = List.of(7);
        final Computer computer = Computer.builder().input(inputDevice).memory(memory).build();

        assertEquals(Processor.State.RUNNABLE, computer.state());
        computer.run();
        assertEquals(Processor.State.EXITED, computer.state());
        assertEquals(List.of(7), computer.outputDump());
        assertEquals(List.of(), computer.inputDump());
    }

    @Test
    public void testResumableComputer() {
        final List<Integer> memory = List.of(3, 5, 4, 5, 99, 0);
        final List<Integer> inputDevice = List.of();
        final Computer computer = Computer.resumableBuilder().input(inputDevice).memory(memory).build();

        assertEquals(Processor.State.RUNNABLE, computer.state());
        computer.run();
        assertEquals(Processor.State.RUNNABLE, computer.state());
        assertEquals(List.of(), computer.inputDump());
        assertEquals(List.of(), computer.outputDump());
        computer.provideInpute(7);
        assertEquals(List.of(7), computer.inputDump());
        computer.run();
        assertEquals(Processor.State.EXITED, computer.state());
        assertEquals(List.of(7), computer.outputDump());
        assertEquals(List.of(), computer.inputDump());
    }
}
