package com.faza.example.simple.multiband.image.clustering.java.application.command;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 28 May 2018
 */

public interface Command<REQUEST, RESPONSE> {

    RESPONSE execute(REQUEST request) throws Exception;
}
