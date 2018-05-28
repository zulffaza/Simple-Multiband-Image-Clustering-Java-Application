package com.faza.example.simple.multiband.image.clustering.java.application.main;

import com.faza.example.simple.multiband.image.clustering.java.application.command.executor.CommandExecutor;
import com.faza.example.simple.multiband.image.clustering.java.application.command.executor.implementation.CommandExecutorImpl;
import com.faza.example.simple.multiband.image.clustering.java.application.command.implementation.ClusterCommand;
import com.faza.example.simple.multiband.image.clustering.java.application.command.implementation.ReadDataSetCommand;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ClusterRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ReadDataSetRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ClusterResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ReadDataSetResponse;
import com.faza.example.simple.multiband.image.clustering.java.application.command.util.CommandExceptionHelper;
import com.faza.example.simple.multiband.image.clustering.java.application.model.Cluster;
import com.faza.example.simple.multiband.image.clustering.java.application.model.Pixel;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.ClusterStrategy;
import com.faza.example.simple.multiband.image.clustering.java.application.strategy.implementation.CentroidLinkageStrategy;

import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class Application {

    private static Application instance;

    private Application() {

    }

    private static Application getInstance() {
        if (instance == null)
            instance = new Application();

        return instance;
    }

    public static void main(String[] args) {
        Application application = Application.getInstance();

        CommandExecutor commandExecutor = CommandExecutorImpl.getInstance();
        CommandExceptionHelper commandExceptionHelper = CommandExceptionHelper.getInstance();

        ReadDataSetRequest readDataSetRequest = application.createReadDataSetRequest(args);
        ReadDataSetResponse readDataSetResponse = application.readDataSet(readDataSetRequest, commandExecutor,
                commandExceptionHelper);

        ClusterRequest clusterRequest = application.createClusterRequest(readDataSetResponse.getNumberOfCluster(),
                readDataSetResponse.getPixels(), CentroidLinkageStrategy.getInstance());
        ClusterResponse clusterResponse = application.cluster(clusterRequest, commandExecutor,
                commandExceptionHelper);

        application.printClusters(clusterResponse.getClusters());
    }

    private ReadDataSetRequest createReadDataSetRequest(String[] inputs) {
        return ReadDataSetRequest.builder()
                .inputs(inputs)
                .build();
    }

    private ReadDataSetResponse readDataSet(ReadDataSetRequest readDataSetRequest,
                                            CommandExecutor commandExecutor,
                                            CommandExceptionHelper commandExceptionHelper) {
        ReadDataSetResponse readDataSetResponse = ReadDataSetResponse.builder()
                .build();

        try {
            readDataSetResponse = commandExecutor.doExecute(
                    ReadDataSetCommand.class, readDataSetRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running ReadDataSetCommand...");
        }

        return readDataSetResponse;
    }

    private ClusterRequest createClusterRequest(Integer numberOfCluster, List<Pixel> pixels,
                                                ClusterStrategy clusterStrategy) {
        return ClusterRequest.builder()
                .numberOfCluster(numberOfCluster)
                .pixels(pixels)
                .clusterStrategy(clusterStrategy)
                .build();
    }

    private ClusterResponse cluster(ClusterRequest clusterRequest,
                                    CommandExecutor commandExecutor,
                                    CommandExceptionHelper commandExceptionHelper) {
        ClusterResponse clusterResponse = ClusterResponse.builder()
                .build();

        try {
            clusterResponse = commandExecutor.doExecute(
                    ClusterCommand.class, clusterRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running ClusterCommand...");
        }

        return clusterResponse;
    }

    private void printClusters(List<Cluster> clusters) {
        clusters.forEach(this::printPixels);
    }

    private void printPixels(Cluster cluster) {
        cluster.getPixels().forEach(this::printPixelId);
        printNewLine();
    }

    private void printPixelId(Pixel pixel) {
        System.out.print(pixel.getId() + " ");
    }

    private void printNewLine() {
        System.out.println();
    }
}
