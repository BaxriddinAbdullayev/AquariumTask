package com.aquariumtask.utils;

import com.aquariumtask.domain.model.Coordinate;

import java.util.Random;

public class RandomUtils {

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static synchronized Coordinate generateCoordinate(int aqWidth, int aqDepth, int aqHieght) {
        Coordinate coordinate = new Coordinate();
        coordinate.setX(generateRandomNumber(1, aqWidth - 1));
        coordinate.setY(generateRandomNumber(1, aqDepth - 1));
        coordinate.setZ(generateRandomNumber(1, aqHieght - 1));
        return coordinate;
    }
}
