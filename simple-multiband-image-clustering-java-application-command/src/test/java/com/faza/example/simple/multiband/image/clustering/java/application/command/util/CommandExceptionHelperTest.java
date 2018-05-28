package com.faza.example.simple.multiband.image.clustering.java.application.command.util;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public class CommandExceptionHelperTest {

    @Rule
    public final ExpectedSystemExit expectedSystemExit = ExpectedSystemExit.none();

    private static final Integer EXIT_STATUS = -1;

    private CommandExceptionHelper commandExceptionHelper;

    @Before
    public void setUp() {
        commandExceptionHelper = CommandExceptionHelper.getInstance();
    }

    @Test
    public void printMessage_success() {
        expectedSystemExit.expectSystemExitWithStatus(EXIT_STATUS);
        commandExceptionHelper.printMessage("Error message");
    }

    @Test
    public void printMessage_success_emptyMessage() {
        expectedSystemExit.expectSystemExitWithStatus(EXIT_STATUS);
        commandExceptionHelper.printMessage("");
    }

    @Test
    public void printMessage_success_nullMessage() {
        expectedSystemExit.expectSystemExitWithStatus(EXIT_STATUS);
        commandExceptionHelper.printMessage(null);
    }
}