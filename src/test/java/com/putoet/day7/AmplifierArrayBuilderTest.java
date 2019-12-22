package com.putoet.day7;

import org.junit.Test;

import java.util.List;

import static com.putoet.day7.ExceptionTester.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AmplifierArrayBuilderTest {

    public static final String MISSING_BUILD_PARAMETERS = "Missing build parameters";

    @Test
    public void testBuilderEmptySequence() {
        IllegalArgumentException ia = ia(() -> AmplifierArray.builder().sequence(null));
        assertNotNull(ia);
        assertEquals("Invalid sequence", ia.getMessage());

        ia = ia(() -> AmplifierArray.builder().sequence(List.of()));
        assertNotNull(ia);
        assertEquals("Invalid sequence", ia.getMessage());

        ia = ia(() -> AmplifierArray.builder().sequence(List.of(4, 2)));
        assertNotNull(ia);
        assertEquals("Invalid sequence", ia.getMessage());
    }

    @Test
    public void testBuilderInvalidSequence() {
        IllegalArgumentException ia = ia(() -> AmplifierArray.builder().sequence(List.of(0, 0, 1, 2, 3)));
        assertNotNull(ia);
        assertEquals("Sequence should contain 5 unique values of 0..4", ia.getMessage());

        ia = ia(() -> AmplifierArray.builder().sequence(List.of(1, 2, 3, 4, 5)));
        assertNotNull(ia);
        assertEquals("Sequence should contain 5 unique values of 0..4", ia.getMessage());

        IllegalStateException is = is(() -> AmplifierArray.builder()
                .sequence(List.of(0, 1, 2, 3, 4))
                .sequence(List.of()));
        assertNotNull(is);
        assertEquals("Sequence already set", is.getMessage());
    }

    @Test
    public void testBuilderInvalidIntCode() {
        IllegalArgumentException ia = ia(() -> AmplifierArray.builder().intCode(null));
        assertNotNull(ia);
        assertEquals("Invalid program code", ia.getMessage());

        ia = ia(() -> AmplifierArray.builder().intCode(List.of()));
        assertNotNull(ia);
        assertEquals("Invalid program code", ia.getMessage());

        IllegalStateException is = is(() -> AmplifierArray.builder()
                .intCode(List.of(99))
                .intCode(List.of()));
        assertNotNull(is);
        assertEquals("IntCode already set", is.getMessage());
    }

    @Test
    public void testBuilderInvalidInputSignal() {
        IllegalStateException is = is(() -> AmplifierArray.builder()
                .inputSignal(0)
                .inputSignal(0));
        assertNotNull(is);
        assertEquals("Input signal already set", is.getMessage());
    }

    @Test
    public void testBuilderMissingParameter() {
        IllegalStateException is = is(() -> AmplifierArray.builder().sequence(List.of(0, 1, 2, 3, 4)).intCode(List.of(99)).build());
        assertEquals(MISSING_BUILD_PARAMETERS, is.getMessage());
        is = is(() -> AmplifierArray.builder().sequence(List.of(0, 1, 2, 3, 4)).inputSignal(0).build());
        assertEquals(MISSING_BUILD_PARAMETERS, is.getMessage());
        is = is(() -> AmplifierArray.builder().intCode(List.of(99)).inputSignal(0).build());
        assertEquals(MISSING_BUILD_PARAMETERS, is.getMessage());
    }

    @Test
    public void testBuilder() {
        AmplifierArray.builder().sequence(List.of(0, 1, 2, 3, 4)).intCode(List.of(99)).inputSignal(0);
    }
}
