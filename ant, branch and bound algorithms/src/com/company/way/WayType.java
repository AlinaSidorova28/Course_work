package com.company.way;

import com.company.creator.Creator;
import com.company.entity.Ant;
import com.company.update.Update;

import java.util.ArrayList;

public class WayType {
    public static double ALPHA = 1;
    public static int BETTA = 1;
    public static int ANT_AMOUNT = 20;

    private ArrayList<Integer> unvisited;

    public WayType(int n) {
        this.unvisited = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            this.unvisited.add(i);
        }
    }

    public ArrayList<Integer> getUnvisited() {
        return unvisited;
    }

    public void setUnvisited(ArrayList<Integer> unvisited) {
        this.unvisited = unvisited;
    }

    // defines probability of going to vertex 'to' from the last visited one
    public int probability(Ant ant, int to, ArrayList<ArrayList<Double>> pheromone, ArrayList<ArrayList<Double>> distance) {
        int from = ant.getPath().get(ant.getPath().size() - 1);
        double sum = 0;
        for (int item : unvisited) {
            sum += Math.pow(pheromone.get(from - 1).get(item - 1), ALPHA) * Math.pow(1.0 / distance.get(from - 1).get(item - 1), BETTA);
        }
        double numerator = Math.pow(pheromone.get(from - 1).get(to - 1), ALPHA) * Math.pow(1.0 / distance.get(from - 1).get(to - 1), BETTA);
        double Pij = numerator / sum;
        return (int) Math.round(Pij * 100);
    }

    public Ant findShortestWay(Creator creator, int n) {
        Update update = new Update();
        double length = 0;
        Ant bestAnt = new Ant();
        for (int k = 0; k < ANT_AMOUNT; k++) {
            Ant ant = new Ant();
            WayType wayType = new WayType(n);
            creator.createPath(ant, wayType, creator);
            update.updatePheromone(ant, creator, n);
            if (length == 0) {
                bestAnt = ant;
                length = ant.getLength();
            } else if (ant.getLength() < length) {
                length = ant.getLength();
                bestAnt = ant;
            }
        }
        return bestAnt;
    }
}
