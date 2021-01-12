package com.putoet.day11;

public class Panel {
    private PanelColor color = PanelColor.BLACK;
    private boolean painted = false;

    public Panel() {
    }

    private Panel(PanelColor color) {
        this.color = color;
    }

    public static Panel blackPanel() {
        return new Panel(PanelColor.BLACK);
    }

    public static Panel whitePanel() {
        return new Panel(PanelColor.WHITE);
    }

    public void paint(PanelColor color) {
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
