package com.putoet.day11;

import org.jetbrains.annotations.NotNull;

class Panel {
    private PanelColor color;
    private boolean painted = false;

    private Panel(@NotNull PanelColor color) {
        this.color = color;
    }

    public static Panel blackPanel() {
        return new Panel(PanelColor.BLACK);
    }

    public static Panel whitePanel() {
        return new Panel(PanelColor.WHITE);
    }

    public void paint(@NotNull PanelColor color) {
        this.color = color;
        painted = true;
    }

    public PanelColor color() {
        return color;
    }

    public boolean painted() {
        return painted;
    }
}
