package com.putoet.day15;

public interface IExtendableSurface {
    long count(Paintable tile);

    Paintable at(int x, int y);

    void paint(int x, int y, Paintable newPaintable);
}
