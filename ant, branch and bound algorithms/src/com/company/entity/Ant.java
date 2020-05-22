package com.company.entity;

import com.company.creator.Creator;
import com.company.way.WayType;

import java.util.ArrayList;

public class Ant {
    private ArrayList<Integer> path;
    private double length;

    public Ant() {
        this.path = new ArrayList<>();
        this.path.add(1);
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void chooseVertex(Creator creator, Ant ant, WayType wayType) {
        int prob = (int) ( 1 + Math.random() * 99 );
        ArrayList<Integer> P = creator.createProbabilityArray(ant, wayType);
        ArrayList<Integer> unvisited = wayType.getUnvisited();

        for(int i = 0; i < P.size() - 1; i++) {
            if(prob >= P.get(i) && prob <= P.get(i + 1)) {
                this.path.add(unvisited.get(i));
                unvisited.remove(i);
                break;
            }
        }
    }
}
