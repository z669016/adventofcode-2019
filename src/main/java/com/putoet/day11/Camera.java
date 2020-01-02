package com.putoet.day11;

import com.putoet.day9.IInputDevice;

import java.util.Optional;

public class Camera implements IInputDevice {
    private final Surface surface;

    public Camera(Surface surface) {
        this.surface = surface;
    }

    public Color color() {
        return surface.panelColor();
    }

    @Override
    public Optional<Long> get() {
        return Optional.of(surface.panelColor() == Color.BLACK ? 0L : 1L);
    }

    @Override
    public void put(Long value) {
        throw new RuntimeException("Method put() on this input device is not implemented.");
    }
}
