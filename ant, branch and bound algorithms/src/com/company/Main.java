package com.company;

import com.company.creator.Creator;
import com.company.entity.Ant;

import com.company.entity.BranchAndBound;
import com.company.handler.MatrixHandler;
import com.company.reader.InfoReader;
import com.company.way.WayType;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int n;
        InfoReader reader = new InfoReader();
        n = reader.readVertex();
        Creator creator = new Creator(n);
        for (ArrayList<Double> d : creator.getDistance()) {
            System.out.print("{");
            for (double item : d) {
                System.out.printf("%8.0f ", item);
            }
            System.out.println("}");
        }

        // Ant algorithm
        long start = System.currentTimeMillis();
        WayType wayType = new WayType(n);
        Ant ant = wayType.findShortestWay(creator, n);
        long timeSpent = System.currentTimeMillis() - start;
        System.out.println("\nAnt algorithm\nBest path: " + ant.getPath());
        System.out.printf("Length: %1.0f", ant.getLength());
        System.out.println("\nTime spent: " + timeSpent + " milliseconds");

        // Branch and bound algorithm
        start = System.currentTimeMillis();
        BranchAndBound branch = new BranchAndBound(creator.getDistance());
        MatrixHandler matrixHandler = new MatrixHandler();
        for (int i = 1; i < n; i++) {
            ArrayList<Integer> path = new ArrayList<>();
            ArrayList<ArrayList<Double>> newMatrix = new ArrayList<>();
            matrixHandler.copyMatrix(branch.getMatrix(), newMatrix);
            matrixHandler.setY(newMatrix, 0, i);

            path.add(1);
            path.add(i + 1);
            branch.matrixUpdate(matrixHandler.getY(), path, branch.getMatrix().get(0).get(i));
        }
        timeSpent = System.currentTimeMillis() - start;
        System.out.println("\nBranch and bound algorithm\nBest path: " + branch.getRecord());
        System.out.printf("Length: %1.0f", branch.getRecordWeight());
        System.out.println("\nTime spent: " + timeSpent + " milliseconds");
    }
}
