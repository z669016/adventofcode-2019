package com.putoet.day7;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class PhaseSetting {
    private final List<Integer> setting;

    public PhaseSetting(@NotNull List<Integer> setting) {
        this.setting = List.copyOf(setting);
    }

    public int get(int i) {
        assert i >=0 && i < setting.size();

        return setting.get(i);
    }

    public int size() {
        return setting.size();
    }
}
