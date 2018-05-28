package com.faza.example.simple.multiband.image.clustering.java.application.strategy;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 27 May 2018
 */

public interface Strategy<REQUEST, RESPONSE> {

    RESPONSE execute(REQUEST request) throws Exception;
}
