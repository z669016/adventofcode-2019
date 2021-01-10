package com.putoet.day8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Image {
    private final Size size;
    private final List<Layer> layers;

    public static Image of(Size size, List<Integer> pixels) {
        assert (pixels.size() % size.size() == 0);

        final List<Layer> layers = new ArrayList<>();
        for (int idx = 0; idx < pixels.size(); idx += size.size()) {
            layers.add(Layer.of(size, pixels.subList(idx, idx + size.size())));
        }

        return new Image(size, layers);
    }

    public Image(Size size, List<Layer> layers) {
        this.size = size;
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
        return size.size() * layersCount();
    }

    public Layer decode() {
        final List<Integer> decodedLayer = new ArrayList<>();
        for (int idy = 0; idy < size.height(); idy++) {
            for (int idx = 0; idx < size.width(); idx++) {
                final int finalIdx = idx;
                final int finalIdy = idy;
                decodedLayer.add(layers.stream()
                        .map(layer -> layer.pixel(finalIdx, finalIdy))
                        .reduce(2, (p1, p2) -> p1 != 2 ? p1 : p2));
            }
        }
        return Layer.of(size, decodedLayer);
    }

    public Optional<Layer> findLayerWithLowerNumberOf(int pixelValue) {
        return layers.stream().min(Comparator.comparingInt(layer -> layer.count(pixelValue)));
    }
}
