package com.putoet.day8;

import com.putoet.grid.Size;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

record Image(@NotNull Size dimension, @NotNull List<Layer> layers) {
    public static Image of(Size size, List<Integer> pixels) {
        assert (pixels.size() % size.count() == 0);

        final var layers = new ArrayList<Layer>();
        for (var idx = 0; idx < pixels.size(); idx += (int) size.count()) {
            layers.add(Layer.of(size, pixels.subList(idx, idx + (int) size.count())));
        }

        return new Image(size, layers);
    }

    public Layer layer(int number) {
        assert (number >= 0 && number < layers.size());

        return layers.get(number);
    }

    public int layersCount() {
        return layers.size();
    }

    public int size() {
        return (int) dimension.count() * layersCount();
    }

    public Layer decode() {
        final var decodedLayer = new ArrayList<Integer>();
        for (var idy = 0; idy < dimension.dy(); idy++) {
            for (var idx = 0; idx < dimension.dx(); idx++) {
                final var finalIdx = idx;
                final var finalIdy = idy;
                decodedLayer.add(layers.stream()
                        .map(layer -> layer.pixel(finalIdx, finalIdy))
                        .reduce(2, (p1, p2) -> p1 != 2 ? p1 : p2));
            }
        }
        return Layer.of(dimension, decodedLayer);
    }

    public Optional<Layer> findLayerWithLowerNumberOf(int pixelValue) {
        return layers.stream().min(Comparator.comparingInt(layer -> layer.count(pixelValue)));
    }
}
