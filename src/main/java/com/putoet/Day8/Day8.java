package com.putoet.Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) throws IOException {
        System.out.println("[Day8 version 1.0]");

        final Optional<String> line = Files.lines(Paths.get("password.txt")).findFirst();
        if (line.isEmpty()) {
            System.out.println("No image available");
            return;
        }

        final List<Integer> pixels = line.get().chars().map(c -> c - '0').mapToObj(Integer::valueOf).collect(Collectors.toList());
        final Image image = Image.of(Dimension.of(25, 6), pixels);

        final Layer layer = image.findLayerWithLowerNumberOf(0).get();
        layer.dump();

        final int result = layer.count(1) * layer.count(2);
        System.out.println("Result for 8a is " + result);
    }
}
