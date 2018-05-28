package com.faza.example.simple.multiband.image.clustering.java.application.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 10 May 2018
 */

@Getter
@Setter
@EqualsAndHashCode
public class Cluster {

    private Integer id;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Boolean needCentroid;

    @Setter(AccessLevel.NONE)
    private Pixel centroid;

    private List<Pixel> pixels;

    private List<ClusterDistance> clusterDistances;

    private Cluster(ClusterBuilder clusterBuilder) {
        this.id = clusterBuilder.id;
        this.needCentroid = clusterBuilder.needCentroid;
        this.pixels = clusterBuilder.pixels;
        this.clusterDistances = clusterBuilder.clusterDistances;

        sortPixels();
        sortClusterDistances();

        buildCentroid();
    }

    public static class ClusterBuilder {

        private Integer id;

        private Boolean needCentroid = Boolean.FALSE;

        private List<Pixel> pixels;

        private List<ClusterDistance> clusterDistances = new ArrayList<>();

        private ClusterBuilder(List<Pixel> pixels) {
            this(0, pixels);
        }

        private ClusterBuilder(Integer id, List<Pixel> pixels) {
            this.id = id;
            this.pixels = pixels;
        }

        public ClusterBuilder needCentroid() {
            needCentroid = Boolean.TRUE;
            return this;
        }

        public ClusterBuilder setClusterDistances(List<ClusterDistance> clusterDistances) {
            this.clusterDistances = clusterDistances;
            return this;
        }

        public Cluster build() {
            return new Cluster(this);
        }
    }

    public static ClusterBuilder builder(List<Pixel> pixels) {
        return new ClusterBuilder(pixels);
    }

    public static ClusterBuilder builder(Integer id, List<Pixel> pixels) {
        return new ClusterBuilder(id, pixels);
    }

    public void setPixels(List<Pixel> pixels) {
        this.pixels = pixels;
        sortPixels();

        buildCentroid();
    }

    public void addPixel(Pixel pixel) {
        this.pixels.add(pixel);
        sortPixels();

        buildCentroid();
    }

    public Pixel getPixel(Integer id) {
        return this.pixels.stream()
                .filter(pixel ->
                        isIdEquals(pixel.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void setClusterDistances(List<ClusterDistance> clusterDistances) {
        this.clusterDistances = clusterDistances;
        sortClusterDistances();
    }

    public void addClusterDistance(ClusterDistance clusterDistance) {
        this.clusterDistances.add(clusterDistance);
        sortClusterDistances();
    }

    public ClusterDistance getClusterDistance(Integer id) {
        return this.clusterDistances.stream()
                .filter(clusterDistance ->
                        isIdEquals(clusterDistance.getId(), id))
                .findFirst()
                .orElse(null);
    }

    private void buildCentroid() {
        if (this.needCentroid) {
            this.centroid = createInitialCentroid();

            sumAllPixelsField();
            dividePixelsWithPixelsSize();
        }
    }

    private Pixel createInitialCentroid() {
        List<Double> centroidPixels = createInitialPixels();

        return Pixel.builder(centroidPixels)
                .build();
    }

    private List<Double> createInitialPixels() {
        Integer pixelSize = getPixelSize();

        return new ArrayList<>(
                Collections.nCopies(pixelSize, 0.00));
    }

    private Integer getPixelSize() {
        return this.pixels.stream()
                .max(Comparator.comparingDouble(this::getBytesSize))
                .map(this::getBytesSize)
                .orElse(null);
    }

    private Integer getBytesSize(Pixel pixel) {
        return pixel.getBytes().size();
    }

    private void sumAllPixelsField() {
        this.pixels.forEach(this::sumCentroidPixels);
    }

    private void sumCentroidPixels(Pixel pixel) {
        for (Integer i = 0; i < pixel.getBytes().size(); i++)
            sumCentroidPixel(pixel, i);
    }

    private void sumCentroidPixel(Pixel pixel, Integer index) {
        Double newValue = this.centroid.getBytes().get(index) + pixel.getBytes().get(index);
        this.centroid.getBytes().set(index, newValue);
    }

    private void dividePixelsWithPixelsSize() {
        Integer pixelsSize = this.pixels.size();
        Integer centroidPixelsSize = this.centroid.getBytes().size();

        for (Integer i = 0; i < centroidPixelsSize; i++)
            dividePixelWithPixelsSize(pixelsSize, i);
    }

    private void dividePixelWithPixelsSize(Integer pixelsSize, Integer index) {
        Double newValue = this.centroid.getBytes().get(index) / pixelsSize;
        this.centroid.getBytes().set(index, newValue);
    }

    private void sortPixels() {
        this.pixels.sort(
                Comparator.comparingInt(Pixel::getId));
    }

    private void sortClusterDistances() {
        this.clusterDistances.sort(
                Comparator.comparingInt(ClusterDistance::getId));
    }

    private Boolean isIdEquals(Integer id, Integer searchId) {
        return id.equals(searchId);
    }

    @Override
    public String toString() {
        return "Cluster-" + this.id;
    }
}
