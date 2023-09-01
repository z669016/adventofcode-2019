package com.putoet.day11;

import com.putoet.intcode.InputDevice;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

class Camera implements InputDevice {
    private final Surface surface;

    public Camera(@NotNull Surface surface) {
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
    public OptionalLong poll(int timeout, @NotNull TimeUnit timeUnit) {
        return OptionalLong.of(surface.panelColor() == PanelColor.BLACK ? 0L : 1L);
    }

    @Override
    public int size() {
        return 1;
    }
}
