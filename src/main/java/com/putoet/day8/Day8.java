package com.putoet.day8;

import com.putoet.grid.Size;
import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day8 {
    public static void main(String[] args) {
        final var pixels = ResourceLines.line("/day8.txt").chars().map(c -> c - '0').boxed().toList();
        final var image = Image.of(new Size(25, 6), pixels);

        Timer.run(() -> part1(image));
        Timer.run(() -> part2(image));
    }

    static void part1(Image image) {
        final var layer = image.findLayerWithLowerNumberOf(0).orElseThrow();
        final var resultA = layer.count(1) * layer.count(2);
        System.out.println("Result for 8a is " + resultA);
    }

    static void part2(Image image) {
        System.out.println("Result for 8b is:");
        final var decodedLayer = image.decode();
        decodedLayer.dumpAsImage();
    }
}
