package com.putoet.day15;

public interface Paintable {
    char paint();

    static Paintable unnkown() {
        return () -> ' ';
    }
}