package com.putoet.day11;

public class Panel {
    private Color color = Color.BLACK;
    private boolean painted = false;

    public Panel() {}

    public static Panel blackPanel() {
        return new Panel();
    }

    public static Panel whitePanel() {
        final Panel newPanel = new Panel();
        newPanel.paint(Color.WHITE);
        return newPanel;
    }

    public void paint(Color color) {
        this.color = color;
        painted = true;
    }

    public Color color() { return color; }

    public boolean painted() { return painted; }
}
