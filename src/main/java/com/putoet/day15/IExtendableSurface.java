package com.putoet.day15;

public interface IExtendableSurface {
    long count(Paintable tile);

    Paintable at(Point point);

    void paint(Point point, Paintable newPaintable);
}
