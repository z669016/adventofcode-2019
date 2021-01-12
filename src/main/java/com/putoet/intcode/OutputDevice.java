package com.putoet.intcode;

import java.util.List;
import java.util.stream.Stream;

public interface OutputDevice {
    void offer(long value);
    int size();
    List<Long> asList();
}
