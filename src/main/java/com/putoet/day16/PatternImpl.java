package com.putoet.day16;

import java.util.Iterator;
import java.util.List;

public class PatternImpl implements Pattern {
    private final List<Integer> list;
    private final int phase;

    public PatternImpl(List<Integer> list) {
        this.list = list;
        this.phase = 1;
    }

    private PatternImpl(List<Integer> list, int phase) {
        this.list = list;
        this.phase = phase;
    }

    @Override
    public Pattern nextPhasePattern() {
        return new PatternImpl(list, phase + 1);
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int offset = 0;
            int iteratorPhase = phase;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                offset = (offset + 1) % (list.size() * iteratorPhase);
                int idx = offset / iteratorPhase;
                return list.get(idx);
            }
        };
    }
}
