package com.faza.example.simple.multiband.image.clustering.java.application.command.model.request;

import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.ClusterStrategy;
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
public class ClusterCommandRequest {

    private Integer numberOfCluster;

    private List<Pixel> pixels;

    private ClusterStrategy clusterStrategy;
}
