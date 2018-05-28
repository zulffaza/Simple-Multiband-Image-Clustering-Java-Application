package com.faza.example.simple.multiband.image.clustering.java.application.strategy.implementation;

import com.faza.example.simple.multiband.image.clustering.java.application.model.Cluster;
import com.faza.example.simple.multiband.image.clustering.java.application.model.ClusterDistance;
import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.ClusterStrategy;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.model.request.ClusterStrategyRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.model.response.ClusterStrategyResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.util.ClustersHelper;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.util.PixelsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 11 May 2018
 */

public class CentroidLinkageStrategy implements ClusterStrategy {

    private static final Integer CLUSTER_INITIAL_ID = 1;
    private static final Integer FIRST_INDEX = 0;

    private static CentroidLinkageStrategy instance;

    private CentroidLinkageStrategy() {

    }

    public static CentroidLinkageStrategy getInstance() {
        if (instance == null)
            instance = new CentroidLinkageStrategy();

        return instance;
    }

    @Override
    public ClusterStrategyResponse execute(ClusterStrategyRequest clusterStrategyRequest) throws Exception {
        List<Cluster> clusters = createInitialCluster(
                clusterStrategyRequest.getPixels());

        doCentroidLinkage(clusters, clusterStrategyRequest);

        return ClusterStrategyResponse.builder()
                .clusters(clusters)
                .build();
    }

    private List<Cluster> createInitialCluster(List<Pixel> irises) {
        AtomicInteger id = new AtomicInteger(CLUSTER_INITIAL_ID);

        return irises.stream()
                .map(iris ->
                        buildCentroidInitialCluster(id.getAndIncrement(), iris))
                .collect(Collectors.toList());
    }

    private Cluster buildCentroidInitialCluster(Integer id, Pixel pixel) {
        List<Pixel> pixels = new ArrayList<>();
        pixels.add(pixel);

        return Cluster.builder(id, pixels)
                .needCentroid()
                .build();
    }

    private void doCentroidLinkage(List<Cluster> clusters, ClusterStrategyRequest clusterStrategyRequest)
            throws Exception {
        ClustersHelper clustersHelper = ClustersHelper.getInstance();

        while (checkClustersSize(clusters, clusterStrategyRequest)) {
            for (Integer i = FIRST_INDEX; isCentroidLinkageComplete(clusters, clusterStrategyRequest, i); i++) {
                doCentroidLinkageIteration(clustersHelper, clusters, i);
            }
        }
    }

    private Boolean checkClustersSize(List<Cluster> clusters, ClusterStrategyRequest clusterStrategyRequest) {
        return clusters.size() != clusterStrategyRequest.getNumberOfCluster();
    }

    private Boolean isCentroidLinkageComplete(List<Cluster> clusters,
                                              ClusterStrategyRequest clusterStrategyRequest,
                                              Integer iteration) {
        return iteration < clusters.size() && checkClustersSize(clusters, clusterStrategyRequest);
    }

    private void doCentroidLinkageIteration(ClustersHelper clustersHelper, List<Cluster> clusters, Integer iteration)
            throws Exception {
        clearCentroidsDistances(clusters);
        calculateCentroidsDistances(clusters);
        clustersHelper.buildNewCluster(clusters.get(iteration), clusters);
    }

    private void clearCentroidsDistances(List<Cluster> clusters) {
        clusters.forEach(cluster ->
                cluster.getClusterDistances().clear());
    }

    private void calculateCentroidsDistances(List<Cluster> clusters) {
        List<Cluster> clustersCopy = new ArrayList<>(clusters);

        clusters.forEach(cluster ->
                calculateCentroidDistances(cluster, clustersCopy));
    }

    private void calculateCentroidDistances(Cluster cluster, List<Cluster> clusters) {
        clusters.forEach(clusterCopy ->
                calculateCentroidDistance(cluster, clusterCopy));
    }

    private void calculateCentroidDistance(Cluster cluster, Cluster clusterCopy) {
        if (!isIrisIdEquals(cluster, clusterCopy)) {
            ClusterDistance clusterDistance = createClusterDistance(cluster, clusterCopy);
            cluster.addClusterDistance(clusterDistance);
        }
    }

    private Boolean isIrisIdEquals(Cluster cluster, Cluster clusterCopy) {
        return cluster.getId().equals(clusterCopy.getId());
    }

    private ClusterDistance createClusterDistance(Cluster cluster, Cluster clusterCopy) {
        Integer id = clusterCopy.getId();
        Double distance = PixelsHelper.getInstance()
                .calculateDistance(cluster.getCentroid(), clusterCopy.getCentroid());

        return ClusterDistance.builder()
                .id(id)
                .distance(distance)
                .build();
    }
}
