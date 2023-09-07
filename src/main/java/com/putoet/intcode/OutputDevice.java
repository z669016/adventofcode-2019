package com.putoet.intcode;

import java.util.List;

public interface OutputDevice {
    void offer(long value);
    int size();
    List<Long> asList();
}
