package com.company.creator;

import com.company.entity.Ant;
import com.company.way.WayType;

import java.util.ArrayList;

public class Creator {
    private ArrayList<ArrayList<Double>> distance;
    private ArrayList<ArrayList<Double>> pheromone;

    public Creator(int n) {
        this.distance = createDistanceMatrix(n);
        this.pheromone = createPheromoneMatrix(n);
    }

    public ArrayList<ArrayList<Double>> getDistance() {
        return distance;
    }

    public void setDistance(ArrayList<ArrayList<Double>> distance) {
        this.distance = distance;
    }

    public ArrayList<ArrayList<Double>> getPheromone() {
        return pheromone;
    }

    public void setPheromone(ArrayList<ArrayList<Double>> pheromone) {
        this.pheromone = pheromone;
    }

    public ArrayList<ArrayList<Double>> createDistanceMatrix(int n) {
        ArrayList<ArrayList<Double>> distance = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            distance.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    distance.get(i).add(Double.POSITIVE_INFINITY);
                } else {
                    distance.get(i).add((double) Math.round(Math.random() * 30 + 1));
                    distance.get(j).add(distance.get(i).get(j));
                }
            }
        }
        return distance;
    }

    public ArrayList<ArrayList<Double>> createPheromoneMatrix(int n) {
        ArrayList<ArrayList<Double>> pheromone = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            pheromone.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    pheromone.get(i).add(0.0);
                } else {
                    pheromone.get(i).add(1.0);
                }
            }
        }
        return pheromone;
    }

    public ArrayList<Integer> createProbabilityArray(Ant ant, WayType wayType) {
        ArrayList<Integer> P = new ArrayList<>();
        ArrayList<Integer> unvisited = wayType.getUnvisited();
        int percentage = 0;
        P.add(0);
        for (int item : unvisited) {
            percentage += wayType.probability(ant, item, pheromone, distance);
            P.add(percentage);
        }
        P.set(P.size() - 1, 100);
        return P;
    }

    public void createPath(Ant ant, WayType wayType, Creator creator) {
        while (wayType.getUnvisited().size() != 0) {
            ant.chooseVertex(creator, ant, wayType);
        }
        ant.getPath().add(1);

        for (int i = 0; i < ant.getPath().size() - 1; i++) {
            ant.setLength(ant.getLength() + distance.get(ant.getPath().get(i) - 1).get(ant.getPath().get(i + 1) - 1));
        }
    }
}
