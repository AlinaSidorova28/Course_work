package com.company.update;

import com.company.creator.Creator;
import com.company.entity.Ant;

import java.util.ArrayList;

public class Update {
    public static double RHO = 0.2;

    public void updatePheromone(Ant ant, Creator creator, int n) {
        double delta = 1.0 / ant.getLength();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i != j){
                    creator.getPheromone().get(i).set(j, (1 - RHO) * creator.getPheromone().get(i).get(j));
                    creator.getPheromone().get(j).set(i, creator.getPheromone().get(i).get(j));
                }
            }
        }
        ArrayList<Integer> path = ant.getPath();
        for (int i = 0; i < path.size() - 1; i++) {
            creator.getPheromone().get(path.get(i) - 1).set(path.get(i + 1) - 1, creator.getPheromone().get(path.get(i) - 1).get(path.get(i + 1) - 1) + delta);
            creator.getPheromone().get(path.get(i + 1) - 1).set(path.get(i) - 1, creator.getPheromone().get(path.get(i) - 1).get(path.get(i + 1) - 1));
        }
    }
}
