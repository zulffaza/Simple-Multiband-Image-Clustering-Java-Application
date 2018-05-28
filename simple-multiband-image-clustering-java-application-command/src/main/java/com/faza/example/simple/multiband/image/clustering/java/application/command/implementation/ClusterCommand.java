package com.faza.example.simple.multiband.image.clustering.java.application.command.implementation;

import com.faza.example.simple.multiband.image.clustering.java.application.command.Command;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ClusterCommandRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ClusterCommandResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.model.request.ClusterStrategyRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.model.response.ClusterStrategyResponse;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class ClusterCommand implements Command<ClusterCommandRequest, ClusterCommandResponse> {

    @Override
    public ClusterCommandResponse execute(ClusterCommandRequest clusterCommandRequest) throws Exception {
        ClusterStrategyRequest clusterStrategyRequest = ClusterStrategyRequest.builder()
                .pixels(clusterCommandRequest.getPixels())
                .numberOfCluster(clusterCommandRequest.getNumberOfCluster())
                .build();
        ClusterStrategyResponse clusterStrategyResponse = clusterCommandRequest.getClusterStrategy()
                .execute(clusterStrategyRequest);

        return ClusterCommandResponse.builder()
                .clusters(clusterStrategyResponse.getClusters())
                .build();
    }
}
