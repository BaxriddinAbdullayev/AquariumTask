package com.aquariumtask.domain.model;


import com.aquariumtask.controller.Aquarium;
import com.aquariumtask.domain.enums.FishGender;
import com.aquariumtask.utils.Constants;
import com.aquariumtask.utils.RandomUtils;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Fish extends Thread {

    private final WeakReference<Aquarium> aquarium;

    private UUID id;
    private FishGender gender;
    private Integer age;
    private Boolean isAlive;
    private Integer ageOfDeath;
    private Coordinate coordinate;

    private Fish(WeakReference<Aquarium> aquarium) {
        this.aquarium = aquarium;
        this.id = UUID.randomUUID();
        this.gender = FishGender.randomGender();
        this.age = 0;
        this.isAlive = true;
        this.ageOfDeath = new Random().nextInt((Constants.upperAge - Constants.lowerAge) + 1) + Constants.lowerAge;
    }

    private Fish(WeakReference<Aquarium> aquarium, FishGender gender) {
        this.aquarium = aquarium;
        this.id = UUID.randomUUID();
        this.gender = gender;
        this.age = 0;
        this.isAlive = true;
        this.ageOfDeath = new Random().nextInt((Constants.upperAge - Constants.lowerAge) + 1) + Constants.lowerAge;
    }

    public static synchronized Fish createFish(WeakReference<Aquarium> aquarium) {
        Fish fish = new Fish(aquarium);
        fish.start();
        return fish;
    }

    public static synchronized Fish createFish(WeakReference<Aquarium> aquarium,FishGender gender) {
        Fish fish = new Fish(aquarium,gender);
        fish.start();
        return fish;
    }

    @Override
    public void run() {

        final AquariumData aquariumData = AquariumData.getInstance();
        CopyOnWriteArrayList<Fish> fishList = Objects.requireNonNull(aquarium.get()).getFishList();

        int pospartiumYear = -2;
        int countForPospartiumYear = -1;
        boolean isBred = false;
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (age >= ageOfDeath) {
                    this.isAlive = false;
                    Thread.currentThread().interrupt();
                    break;
                }

                coordinate = RandomUtils.generateCoordinate(
                        aquariumData.getWidth(),
                        aquariumData.getDepth(),
                        aquariumData.getHeight()
                );

                if (age >= Constants.breedingAge && gender.equals(FishGender.FEMALE) &&
                        (pospartiumYear==-2 || pospartiumYear==-1)) {
                    Fish matchFish = getMatchCoordinate(fishList);

                    if (matchFish != null && isBreedingCompatible(matchFish)) {
                        Fish newFish = Fish.createFish(aquarium);
                        fishList.add(newFish);
                        pospartiumYear = 0;
                        countForPospartiumYear = 0;
                        isBred=true;
                    }
                }

                if(isBred){
                    if(countForPospartiumYear==3){
                        if (pospartiumYear >= 0) {
                            pospartiumYear++;
                        }
                        if (pospartiumYear == Constants.breedingInterval) {
                            pospartiumYear = -1;
                        }
                        countForPospartiumYear=0;
                    }else {
                        countForPospartiumYear++;
                    }
                }


                if (count == 3) {
                    age++;
                    count = 0;
                } else {
                    count++;
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;

            }
        }

    }

    public boolean isBreedingCompatible(Fish fish) {
        if (fish.getGender().equals(FishGender.MALE) &&
                fish.getAge() >= Constants.breedingAge) {
            return true;
        }
        return false;
    }

    public Fish getMatchCoordinate(CopyOnWriteArrayList<Fish> fishList) {
        for (Fish fish : fishList) {
            if (!fish.getFishId().equals(id)) {
                if (fish.getCoordinate().getX().equals(coordinate.getX()) &&
                        fish.getCoordinate().getY().equals(coordinate.getY()) &&
                        fish.getCoordinate().getZ().equals(coordinate.getZ())) {
                    return fish;
                }
            }
        }
        return null;
    }


    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public FishGender getGender() {
        return gender;
    }

    public void setGender(FishGender gender) {
        this.gender = gender;
    }

    public UUID getFishId() {
        return id;
    }

    public void setFishId(UUID id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
