package com.faza.example.simple.multiband.image.clustering.java.application.command.implementation;

import com.faza.example.simple.multiband.image.clustering.java.application.command.Command;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ReadDataSetRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ReadDataSetResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class ReadDataSetCommandTest {

    private Command<ReadDataSetRequest, ReadDataSetResponse> command;

    @Before
    public void setUp() {
        command = new ReadDataSetCommand();
    }

    @Test
    public void readDataSet_success() throws Exception {
        ReadDataSetRequest readDataSetRequest = ReadDataSetRequest.builder()
                .inputs(createInputs())
                .build();

        ReadDataSetResponse expectedReadDataSetResponse = ReadDataSetResponse.builder()
                .numberOfCluster(3)
                .pixels(createExpectedPixels())
                .build();

        ReadDataSetResponse readDataSetResponse = command.execute(readDataSetRequest);

        assertNotNull(readDataSetResponse);
        assertEquals(expectedReadDataSetResponse.getNumberOfCluster(), readDataSetResponse.getNumberOfCluster());
        assertEquals(expectedReadDataSetResponse.getPixels().size(), readDataSetResponse.getPixels().size());

        IntStream.range(0, expectedReadDataSetResponse.getPixels().size())
                .forEach(index -> assertResultPixel(expectedReadDataSetResponse, readDataSetResponse, index));
    }

    @Test
    public void readDataSet_success_bytesIsEmpty() throws Exception {
        String[] inputs = new String[1];
        inputs[0] = "3";

        ReadDataSetRequest readDataSetRequest = ReadDataSetRequest.builder()
                .inputs(inputs)
                .build();

        ReadDataSetResponse expectedReadDataSetResponse = ReadDataSetResponse.builder()
                .numberOfCluster(3)
                .pixels(new ArrayList<>())
                .build();

        ReadDataSetResponse readDataSetResponse = command.execute(readDataSetRequest);

        assertNotNull(readDataSetResponse);
        assertEquals(expectedReadDataSetResponse.getNumberOfCluster(), readDataSetResponse.getNumberOfCluster());
        assertEquals(expectedReadDataSetResponse.getPixels().size(), readDataSetResponse.getPixels().size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readDataSet_failed_inputsIsEmpty() throws Exception {
        String[] inputs = new String[0];

        ReadDataSetRequest readDataSetRequest = ReadDataSetRequest.builder()
                .inputs(inputs)
                .build();

        command.execute(readDataSetRequest);
    }

    private String[] createInputs() {
        String[] inputs = new String[4];

        inputs[0] = "3";
        inputs[1] = "1-2.25";
        inputs[2] = "3.5-4";
        inputs[3] = "5.75-6.000";

        return inputs;
    }

    private List<Pixel> createExpectedPixels() {
        return Arrays.asList(
                createPixel(1, 1D, 2.25),
                createPixel(2, 3.5, 4D),
                createPixel(3, 5.75, 6.000)
        );
    }

    private Pixel createPixel(Integer id, Double... bytes) {
        return Pixel.builder(id, Arrays.asList(bytes))
                .build();
    }

    private void assertResultPixel(ReadDataSetResponse expectedReadDataSetResponse,
                                   ReadDataSetResponse readDataSetResponse, Integer index) {
        Pixel expectedPixel = expectedReadDataSetResponse.getPixels().get(index);
        Pixel pixel = readDataSetResponse.getPixels().get(index);

        assertEquals(expectedPixel.getId(), pixel.getId());
        assertEquals(expectedPixel.getPixelDistances(), pixel.getPixelDistances());

        IntStream.range(0, expectedPixel.getBytes().size())
                .forEach(indexBytes -> assertResultByte(expectedPixel, pixel, indexBytes));
    }

    private void assertResultByte(Pixel expectedPixel, Pixel pixel, Integer index) {
        Double expectedByte = expectedPixel.getBytes().get(index);
        Double resultByte = pixel.getBytes().get(index);

        assertEquals(expectedByte, resultByte);
    }
}