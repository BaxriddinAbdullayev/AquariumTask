package com.aquariumtask.domain.model;

import com.aquariumtask.utils.Constants;
import lombok.Data;

@Data
public class AquariumData {
    private static AquariumData instance;
    private Integer height;
    private Integer width;
    private Integer depth;
    private Integer fishCapacity;

    public AquariumData() {
        this.height = Constants.height;
        this.width = Constants.width;
        this.depth = Constants.depth;
        this.fishCapacity = Constants.fishCapacity;
    }

    public static AquariumData getInstance() {
        if (instance == null) {
            instance = new AquariumData();
        }
        return instance;
    }
}
