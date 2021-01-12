package com.putoet.day11;

import com.putoet.intcode.InputDevice;

import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

public class Camera implements InputDevice {
    private final Surface surface;

    public Camera(Surface surface) {
        this.surface = surface;
    }

    public PanelColor color() {
        return surface.panelColor();
    }

    @Override
    public OptionalLong poll() {
        return OptionalLong.of(surface.panelColor() == PanelColor.BLACK ? 0L : 1L);
    }

    @Override
    public OptionalLong poll(int timeout, TimeUnit timeUnit) {
        return OptionalLong.of(surface.panelColor() == PanelColor.BLACK ? 0L : 1L);
    }

    @Override
    public int size() {
        return 1;
    }
}
