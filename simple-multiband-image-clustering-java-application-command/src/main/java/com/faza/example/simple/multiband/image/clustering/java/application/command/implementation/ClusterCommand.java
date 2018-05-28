package com.faza.example.simple.multiband.image.clustering.java.application.command.implementation;

import com.faza.example.simple.multiband.image.clustering.java.application.command.Command;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ClusterRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ClusterResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.model.Cluster;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.model.request.ClusterStrategyRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.model.response.ClusterStrategyResponse;

import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class ClusterCommand implements Command<ClusterRequest, ClusterResponse> {

    @Override
    public ClusterResponse execute(ClusterRequest clusterRequest) throws Exception {
        ClusterStrategyRequest clusterStrategyRequest = ClusterStrategyRequest.builder()
                .pixels(clusterRequest.getPixels())
                .numberOfCluster(clusterRequest.getNumberOfCluster())
                .build();
        ClusterStrategyResponse clusterStrategyResponse = clusterRequest.getClusterStrategy()
                .execute(clusterStrategyRequest);

        return createClusterResponse(
                clusterStrategyResponse.getClusters());
    }

    private ClusterResponse createClusterResponse(List<Cluster> clusters) {
        return ClusterResponse.builder()
                .clusters(clusters)
                .build();
    }
}
