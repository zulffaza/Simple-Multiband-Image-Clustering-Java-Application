package com.faza.example.simple.multiband.image.clustering.java.application.command.model.response;

import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

@Data
@Builder
public class ReadDataSetResponse {

    private Integer numberOfCluster;

    private List<Pixel> pixels;
}
