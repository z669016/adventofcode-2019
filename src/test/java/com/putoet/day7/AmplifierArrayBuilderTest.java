package com.putoet.day7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AmplifierArrayBuilderTest {

    public static final String MISSING_BUILD_PARAMETERS = "Missing build parameters";

    @Test
    public void testBuilderEmptySequence() {
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().sequence(null));
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().sequence(List.of()));
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().sequence(List.of(4, 2)));
    }

    @Test
    public void testBuilderInvalidSequence() {
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().sequence(List.of(0, 0, 1, 2, 3)));
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().sequence(List.of(1, 2, 3, 4, 5)));

        assertThrows(IllegalStateException.class, () -> AmplifierArray.builder()
                .sequence(List.of(0, 1, 2, 3, 4))
                .sequence(List.of()));
    }

    @Test
    public void testBuilderInvalidIntCode() {
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().intCode(null));
        assertThrows(IllegalArgumentException.class, () -> AmplifierArray.builder().intCode(List.of()));

        assertThrows(IllegalStateException.class, () -> AmplifierArray.builder()
                .intCode(List.of(99))
                .intCode(List.of()));
    }

    @Test
    public void testBuilderInvalidInputSignal() {
        assertThrows(IllegalStateException.class, () -> AmplifierArray.builder()
                .inputSignal(0)
                .inputSignal(0));
    }

    @Test
    public void testBuilderMissingParameter() {
        assertThrows(IllegalStateException.class, () -> AmplifierArray.builder().sequence(List.of(0, 1, 2, 3, 4)).intCode(List.of(99)).build());
        assertThrows(IllegalStateException.class, () -> AmplifierArray.builder().sequence(List.of(0, 1, 2, 3, 4)).inputSignal(0).build());
        assertThrows(IllegalStateException.class, () -> AmplifierArray.builder().intCode(List.of(99)).inputSignal(0).build());
    }

    @Test
    public void testBuilder() {
        AmplifierArray.builder().sequence(List.of(0, 1, 2, 3, 4)).intCode(List.of(99)).inputSignal(0);
    }
}
