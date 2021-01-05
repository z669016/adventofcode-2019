package com.putoet.day8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Image {
    private final Dimension dimension;
    private final List<Layer> layers;

    public static Image of(Dimension dimension, List<Integer> pixels) {
        assert (pixels.size() % dimension.size() == 0);

        final List<Layer> layers = new ArrayList<>();
        for (int idx = 0; idx < pixels.size(); idx += dimension.size()) {
            layers.add(Layer.of(dimension, pixels.subList(idx, idx + dimension.size())));
        }

        return new Image(dimension, layers);
    }

    public Image(Dimension dimension, List<Layer> layers) {
        this.dimension = dimension;
        this.layers = layers;
    }

    public Layer layer(int number) {
        assert (number >= 0 && number < layers.size());

        return layers.get(number);
    }

    public int layersCount() {
        return layers.size();
    }

    public int size() {
        return dimension.size() * layersCount();
    }

    public Layer decode() {
        final List<Integer> decodedLayer = new ArrayList<>();
        for (int idy = 0; idy < dimension.y(); idy++) {
            for (int idx = 0; idx < dimension.x(); idx++) {
                final int finalIdx = idx;
                final int finalIdy = idy;
                decodedLayer.add(layers.stream()
                        .map(layer -> layer.pixel(finalIdx, finalIdy))
                        .reduce(2, (p1, p2) -> p1 != 2 ? p1 : p2));
            }
        }
        return Layer.of(dimension, decodedLayer);
    }

    public Optional<Layer> findLayerWithLowerNumberOf(int pixelValue) {
        return layers.stream().min(Comparator.comparingInt(l -> l.count(pixelValue)));
    }
}
