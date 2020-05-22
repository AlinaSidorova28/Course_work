package com.company.handler;

import java.util.ArrayList;

public class MatrixHandler {
    private ArrayList<ArrayList<Double>> Y;
    private ArrayList<ArrayList<Double>> N;

    public MatrixHandler(ArrayList<ArrayList<Double>> matrix, int row, int col) {
        setY(matrix, row, col);
        setN(matrix, row, col);
    }

    public MatrixHandler() {}

    public ArrayList<ArrayList<Double>> getY() {
        return Y;
    }

    public void setY(ArrayList<ArrayList<Double>> matrix, int row, int col) {
        this.Y = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            this.Y.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                if (i == row || j == col) {
                    this.Y.get(i).add(Double.POSITIVE_INFINITY);
                } else {
                    this.Y.get(i).add(matrix.get(i).get(j));
                }
            }
        }
        this.Y.get(col).set(row, Double.POSITIVE_INFINITY);
    }

    public ArrayList<ArrayList<Double>> getN() {
        return N;
    }

    public void setN(ArrayList<ArrayList<Double>> matrix, int row, int col) {
        this.N = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            this.N.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                if (i == row && j == col) {
                    this.N.get(i).add(Double.POSITIVE_INFINITY);
                } else {
                    this.N.get(i).add(matrix.get(i).get(j));
                }
            }
        }
    }

    public void copyMatrix(ArrayList<ArrayList<Double>> A, ArrayList<ArrayList<Double>> B) {
        for (int i = 0; i < A.size(); i++) {
            B.add(new ArrayList<>());
            for (int j = 0; j < A.get(i).size(); j++) {
                B.get(i).add(A.get(i).get(j));
            }
        }
    }
}
