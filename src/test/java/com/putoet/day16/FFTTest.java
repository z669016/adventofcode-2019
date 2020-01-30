package com.putoet.day16;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FFTTest {
    private static final Pattern pattern = new PatternImpl(List.of(0, 1, 0, -1));

    @Test(expected = AssertionError.class)
    public void fromNull() {
        FFTImpl.from(null);
    }

    @Test(expected = AssertionError.class)
    public void fromEmpty() {
        FFTImpl.from("");
    }

    @Test(expected = AssertionError.class)
    public void fromInvalid() {
        FFTImpl.from("1a2345");
    }

    @Test
    public void from() {
        assertEquals(List.of(1,2,3,4,5), FFTImpl.from("12345").asList());
    }

    @Test
    public void toStringTest() {
        assertEquals("12345", FFTImpl.from("12345").toString());
    }

    @Test
    public void testApplyPattern() {
        final FFT series = FFTImpl.from("12345678");

        assertEquals("48226158", series.apply(pattern).toString());
        assertEquals("48226158", series.apply(pattern, 1).toString());
    }

    @Test
    public void testApplyPatternTimesFour() {
        FFT series = FFTImpl.from("12345678");
        assertEquals("01029498", series.apply(pattern, 4).toString());
    }

    @Test
    public void testCase1() {
        FFT series = FFTImpl.from("80871224585914546619083218645595");
        assertEquals("24176176", series.apply(pattern, 100).toString().substring(0, 8));
    }

    @Test
    public void testCase2() {
        FFT series = FFTImpl.from("19617804207202209144916044189917");
        assertEquals("73745418", series.apply(pattern, 100).toString().substring(0, 8));
    }

    @Test
    public void testCase3() {
        FFT series = FFTImpl.from("69317163492948606335995924319873");
        assertEquals("52432133", series.apply(pattern, 100).toString().substring(0, 8));
    }
    @Test
    public void testCase1Long() {
        FFT series = FFTImpl.from("03036732577212944063491565474664".repeat(10_000));
        assertEquals("84462026", series.apply(pattern, 100).message(7));
    }

    @Test
    public void testCase2Long() {
        FFT series = FFTImpl.from("02935109699940807407585447034323".repeat(10_000));
        assertEquals("78725270", series.apply(pattern, 100).message(7));
    }

    @Test
    public void testCase3Long() {
        FFT series = FFTImpl.from("03081770884921959731165446850517".repeat(10_000));
        assertEquals("53553731", series.apply(pattern, 100).message(7));
    }
}