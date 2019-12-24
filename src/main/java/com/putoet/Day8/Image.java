package com.putoet.Day8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Image {
    private final Dimension dimension;
    private final List<Layer> layers;

    public static Image of(Dimension dimension, List<Integer> pixels) {
        assert ( pixels.size() % dimension.size() == 0);

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
        assert (number>=0 && number < layers.size());

        return layers.get(number);
    }

    public int layersCount() { return layers.size(); }

    public int size() { return dimension.size() * layersCount(); };

    public Optional<Layer> findLayerWithLowerNumberOf(int pixelValue) {
        return layers.stream().sorted((l1, l2) -> l1.count(pixelValue) - l2.count(pixelValue)).findFirst();
    }
}
