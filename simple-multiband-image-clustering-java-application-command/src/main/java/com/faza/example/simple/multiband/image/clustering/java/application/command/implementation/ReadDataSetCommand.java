package com.faza.example.simple.multiband.image.clustering.java.application.command.implementation;

import com.faza.example.simple.multiband.image.clustering.java.application.command.Command;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ReadDataSetRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ReadDataSetResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class ReadDataSetCommand implements Command<ReadDataSetRequest, ReadDataSetResponse> {

    private static final Integer NUMBER_OF_CLUSTER_INDEX = 0;
    private static final Integer FIRST_INDEX = 0;

    @Override
    public ReadDataSetResponse execute(ReadDataSetRequest readDataSetRequest) {
        List<String> inputs = changeInputsIntoList(
                readDataSetRequest.getInputs());
        Integer numberOfCluster = getAndRemoveNumberOfClusterFromInputs(inputs);
        List<Pixel> pixels = createPixelsFromInput(inputs);

        return createReadDataSetResponse(numberOfCluster, pixels);
    }

    private List<String> changeInputsIntoList(String[] inputs) {
        return new ArrayList<>(Arrays.asList(inputs));
    }

    private Integer getAndRemoveNumberOfClusterFromInputs(List<String> inputs) {
        String numberOfCluster = inputs.remove(NUMBER_OF_CLUSTER_INDEX.intValue()).trim();
        return Integer.valueOf(numberOfCluster);
    }

    private List<Pixel> createPixelsFromInput(List<String> inputs) {
        return IntStream.range(FIRST_INDEX, inputs.size())
                .boxed()
                .map(id ->
                        createPixel(id, inputs.get(id)))
                .collect(Collectors.toList());
    }

    private Pixel createPixel(Integer id, String input) {
        List<Double> bytes = splitBytesFromInput(input);

        return Pixel.builder(++id, bytes)
                .build();
    }

    private List<Double> splitBytesFromInput(String input) {
        return Arrays.stream(input.split("-"))
                .map(String::trim)
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

    private ReadDataSetResponse createReadDataSetResponse(Integer numberOfCluster, List<Pixel> pixels) {
        return ReadDataSetResponse.builder()
                .numberOfCluster(numberOfCluster)
                .pixels(pixels)
                .build();
    }
}
