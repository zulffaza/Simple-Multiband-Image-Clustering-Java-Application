package com.faza.example.simple.multiband.image.clustering.java.application.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 27 May 2018
 */

@Data
@Builder
public class PixelDistance {

    private Integer id;

    private Double distance;
}
