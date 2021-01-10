package com.putoet.day8;

import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day8.txt");
        final List<Integer> pixels = lines.get(0).chars().map(c -> c - '0').boxed().collect(Collectors.toList());
        final Image image = Image.of(Size.of(25, 6), pixels);

        part1(image);
        part2(image);
    }

    private static void part1(Image image) {
        final Layer layer = image.findLayerWithLowerNumberOf(0).get();
        final int resultA = layer.count(1) * layer.count(2);
        System.out.println("Result for 8a is " + resultA);
    }

    private static void part2(Image image) {
        System.out.println("Result for 8b is:");
        final Layer decodedLayer = image.decode();
        decodedLayer.dumpAsImage();
    }
}
