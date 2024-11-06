package com.aquariumtask;

import com.aquariumtask.controller.Aquarium;
import com.aquariumtask.domain.model.Information;

import java.lang.ref.WeakReference;

public class Main {
    public static void main(String[] args) {
        Aquarium aquarium = new Aquarium();
        aquarium.start();

        Information information = new Information(new WeakReference<>(aquarium));
        information.start();
    }
}