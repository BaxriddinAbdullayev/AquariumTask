package com.aquariumtask.controller;

import com.aquariumtask.domain.enums.FishGender;
import com.aquariumtask.domain.model.Fish;
import com.aquariumtask.utils.Constants;
import com.aquariumtask.utils.RandomUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class Aquarium extends Thread {

    private final CopyOnWriteArrayList<Fish> fishList;

    public Aquarium() {
        this.fishList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void run() {
        init();
    }

    private void init() {
        WeakReference<Aquarium> ref = new WeakReference<>(this);
        int N = RandomUtils.generateRandomNumber(1, (Constants.fishCapacity / 2) - 1);
        int M = RandomUtils.generateRandomNumber(1,(Constants.fishCapacity / 2) - N);

        for (int i = 0; i < RandomUtils.generateRandomNumber(1, N); i++) {
            fishList.add(Fish.createFish(ref, FishGender.MALE));
        }

        for (int i = 0; i < RandomUtils.generateRandomNumber(1, M); i++) {
            fishList.add(Fish.createFish(ref, FishGender.FEMALE));
        }
    }

}
