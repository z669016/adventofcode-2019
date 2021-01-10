package com.putoet.day7;

import java.util.ArrayList;
import java.util.List;

public class PhaseSetting {
    private final List<Integer> setting;

    public PhaseSetting(List<Integer> setting) {
        this.setting = new ArrayList<>(setting);
    }

    public int get(int i) {
        assert i >=0 && i < setting.size();

        return setting.get(i);
    }

    public int size() {
        return setting.size();
    }
}
