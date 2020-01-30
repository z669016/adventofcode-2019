package com.putoet.day16;

import java.util.List;

public interface FFT {
    List<Integer> asList();
    FFT apply(Pattern pattern);
    FFT apply(Pattern pattern, int times);
    String message(int offsetSize);
}
