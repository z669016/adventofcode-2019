package com.putoet.day4;

import org.jetbrains.annotations.NotNull;

record Password(@NotNull String value) {
    @Override
    public String toString() {
        return value;
    }
}
