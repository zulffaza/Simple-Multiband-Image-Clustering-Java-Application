package com.faza.example.simple.multiband.image.clustering.java.application.strategy.util;

import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 27 May 2018
 */

public class PixelsHelper {

    private static final Integer FIRST_INDEX = 0;
    private static final Integer DIVIDED_NUMBER = 2;

    private static PixelsHelper instance;

    private PixelsHelper() {

    }

    public static PixelsHelper getInstance() {
        if (instance == null)
            instance = new PixelsHelper();

        return instance;
    }

    public Double calculateDistance(Pixel pixel, Pixel pixelCopy) {
        List<Double> result = sumAllPixels(pixel, pixelCopy);
        Pixel pixelResult = Pixel.builder(result).build();

        return calculateDistance(pixelResult);
    }

    private List<Double> sumAllPixels(Pixel pixel, Pixel pixelCopy) {
        return IntStream.range(FIRST_INDEX, pixel.getBytes().size())
                .mapToDouble(index ->
                        sumPixel(pixel, pixelCopy, index))
                .boxed()
                .collect(Collectors.toList());
    }

    private Double sumPixel(Pixel pixel, Pixel pixelCopy, Integer index) {
        return pixel.getBytes().get(index) + pixelCopy.getBytes().get(index);
    }

    private Double calculateDistance(Pixel pixel) {
        Double distance = divideAllPixels(pixel);

        return Math.sqrt(distance);
    }

    private Double divideAllPixels(Pixel pixel) {
        return IntStream.range(FIRST_INDEX, pixel.getBytes().size())
                .mapToDouble(index ->
                        dividePixel(pixel, index))
                .sum();
    }

    private Double dividePixel(Pixel pixel, Integer index) {
        return pixel.getBytes().get(index) / DIVIDED_NUMBER;
    }
}
