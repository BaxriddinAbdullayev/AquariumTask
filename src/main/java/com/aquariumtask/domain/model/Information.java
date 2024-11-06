package com.aquariumtask.domain.model;

import com.aquariumtask.controller.Aquarium;
import com.aquariumtask.domain.enums.FishGender;
import com.aquariumtask.utils.Constants;
import com.aquariumtask.utils.SystemUtils;
import lombok.Data;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Information extends Thread {

    private final WeakReference<Aquarium> aquarium;
    private CopyOnWriteArrayList<Fish> fishList;
    private Integer aliveFishes = 0;
    private Integer deadFishes = 0;
    private Integer maleFishes = 0;
    private Integer femaleFishes = 0;


    public Information(WeakReference<Aquarium> aquarium) {
        this.aquarium = aquarium;
    }

    @Override
    public void run() {

        boolean isFirstTime = false;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (!isFirstTime) {
                    System.out.println("=====================     Start     =====================\n");
                    Thread.sleep(1000);
                    isFirstTime = true;
                    continue;
                }

                fishList = Objects.requireNonNull(aquarium.get()).getFishList();
                countFishStatistics();

                if (aliveFishes >= Constants.fishCapacity) {
                    SystemUtils.stopProgram();
                }

                System.out.println("Alive fishes : " + aliveFishes);
                System.out.println("Male fishes: " + maleFishes);
                System.out.println("Female fishes: " + femaleFishes);
                System.out.println("Dead Fishes: " + deadFishes + "\n");

                aliveFishes = 0;
                maleFishes = 0;
                femaleFishes = 0;
                deadFishes = 0;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void countFishStatistics() {
        for (Fish fish : fishList) {
            if (fish.getAlive()) {
                aliveFishes++;
                if (fish.getGender().equals(FishGender.MALE)) {
                    maleFishes++;
                } else if (fish.getGender().equals(FishGender.FEMALE)) {
                    femaleFishes++;
                }
            } else if (!fish.getAlive()) {
                deadFishes++;
            }
        }
    }
}
