package com.aquariumtask.domain.enums;

public enum FishGender {
    MALE, FEMALE;

    public static FishGender randomGender() {
        return Math.random() < 0.5 ? MALE : FEMALE;
    }
}
