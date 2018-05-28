package com.faza.example.simple.multiband.image.clustering.java.application.command.executor.implementation;

import com.faza.example.simple.multiband.image.clustering.java.application.command.executor.CommandExecutor;
import com.faza.example.simple.multiband.image.clustering.java.application.command.implementation.ReadDataSetCommand;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.request.ReadDataSetRequest;
import com.faza.example.simple.multiband.image.clustering.java.application.command.model.response.ReadDataSetResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class CommandExecutorImplTest {

    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        commandExecutor = CommandExecutorImpl.getInstance();
    }

    @Test
    public void doExecute_success() throws Exception {
        ReadDataSetRequest readDataSetRequest = ReadDataSetRequest.builder()
                .inputs(createInputs())
                .build();

        ReadDataSetResponse readDataSetResponse = commandExecutor.doExecute(
                ReadDataSetCommand.class, readDataSetRequest);

        assertNotNull(readDataSetResponse);
    }

    @Test(expected = Exception.class)
    public void doExecute_success_throwException() throws Exception {
        ReadDataSetRequest readDataSetRequest = ReadDataSetRequest.builder()
                .build();

        commandExecutor.doExecute(
                ReadDataSetCommand.class, readDataSetRequest);
    }

    private String[] createInputs() {
        String[] inputs = new String[3];

        inputs[0] = "2";
        inputs[1] = "1-2.25";
        inputs[2] = "3.5-4";

        return inputs;
    }
}