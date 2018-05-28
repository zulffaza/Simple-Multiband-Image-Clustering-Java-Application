package com.faza.example.simple.multiband.image.clustering.java.application.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 27 May 2018
 */

@Getter
@Setter
@EqualsAndHashCode
public class Pixel {

    private Integer id;

    private List<Double> bytes;

    private List<PixelDistance> pixelDistances;

    private Pixel(PixelBuilder pixelBuilder) {
        this.id = pixelBuilder.id;
        this.bytes = pixelBuilder.bytes;
        this.pixelDistances = pixelBuilder.pixelDistances;

        sortPixelDistance();
    }

    public static class PixelBuilder {

        private Integer id;

        private List<Double> bytes;

        private List<PixelDistance> pixelDistances = new ArrayList<>();

        private PixelBuilder() {
            this(0, new ArrayList<>());
        }

        private PixelBuilder(List<Double> bytes) {
            this(0, bytes);
        }

        private PixelBuilder(Integer id, List<Double> bytes) {
            this.id = id;
            this.bytes = bytes;
        }

        public PixelBuilder setPixelDistances(List<PixelDistance> pixelDistances) {
            this.pixelDistances = pixelDistances;
            return this;
        }

        public Pixel build() {
            return new Pixel(this);
        }
    }

    public static PixelBuilder builder() {
        return new PixelBuilder();
    }

    public static PixelBuilder builder(List<Double> pixels) {
        return new PixelBuilder(pixels);
    }

    public static PixelBuilder builder(Integer id, List<Double> pixels) {
        return new PixelBuilder(id, pixels);
    }

    public void setPixelDistances(List<PixelDistance> pixelDistances) {
        this.pixelDistances = pixelDistances;
        sortPixelDistance();
    }

    public void addPixelDistance(PixelDistance pixelDistance) {
        this.pixelDistances.add(pixelDistance);
        sortPixelDistance();
    }

    public PixelDistance getPixelDistance(Integer id) {
        return this.pixelDistances.stream()
                .filter(pixelDistance ->
                        isIdEquals(pixelDistance.getId(), id))
                .findFirst()
                .orElse(null);
    }

    private void sortPixelDistance() {
        this.pixelDistances.sort(
                Comparator.comparingInt(PixelDistance::getId));
    }

    private Boolean isIdEquals(Integer id, Integer searchId) {
        return id.equals(searchId);
    }

    @Override
    public String toString() {
        return "Pixel-" + this.id;
    }
}
