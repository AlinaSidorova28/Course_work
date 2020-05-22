package com.company.entity;

import com.company.handler.MatrixHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BranchAndBound {
    private ArrayList<ArrayList<Double>> matrix;
    private ArrayList<Integer> record;
    private double recordWeight;

    public BranchAndBound(ArrayList<ArrayList<Double>> distance) {
        this.matrix = new ArrayList<>();
        for (int i=0; i<distance.size(); i++) {
            this.matrix.add(new ArrayList<>());
            for (int j=0; j<distance.size(); j++) {
                this.matrix.get(i).add(distance.get(i).get(j));
            }
        }
        setRecord();
        setRecordWeight();
    }

    public BranchAndBound(ArrayList<ArrayList<Double>> distance, ArrayList<Integer> record, double record_weight) {
        this.matrix = new ArrayList<>();
        for (int i = 0; i < distance.size(); i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < distance.get(i).size(); j++) {
                this.matrix.get(i).add(distance.get(i).get(j));
            }
        }
        this.record = new ArrayList<>();
        this.record.addAll(record);
        this.recordWeight = record_weight;
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<Double>> matrix) {
        this.matrix = matrix;
    }

    public ArrayList<Integer> getRecord() {
        return record;
    }

    public void setRecord() {
        this.record = new ArrayList<>();
        for (int i = 1; i <= this.matrix.size(); i++) {
            this.record.add(i);
        }
        this.record.add(1);
    }

    public double getRecordWeight() {
        return recordWeight;
    }

    public void setRecordWeight() {
        this.recordWeight = 0;
        for (int i = 0; i < this.record.size() - 1; i++) {
            this.recordWeight += this.matrix.get(this.record.get(i) - 1).get(this.record.get(i + 1) - 1);
        }
    }

    public void matrixUpdate(ArrayList<ArrayList<Double>> matrix, ArrayList<Integer> path, double lowerBound) {
        ArrayList<Double> minRow = new ArrayList<>();
        ArrayList<Double> minColumn = new ArrayList<>();
        lowerBound = findMin(matrix, minRow, lowerBound);
        transpose(matrix);
        lowerBound = findMin(matrix, minColumn, lowerBound);
        transpose(matrix);

        // cut branch if it is longer, than record
        if (lowerBound >= recordWeight) {
            return;
        }

        if (path.size() == record.size()) {
            record.clear();
            record.addAll(path);
            this.recordWeight = lowerBound;
            return;
        }

        if (findZeros(matrix, path).size() != 0) {
            ArrayList<Integer> currentZero = findZeros(matrix, path).get(0);
            MatrixHandler handler = new MatrixHandler(matrix, currentZero.get(0), currentZero.get(1));

            ArrayList<Integer> newPath = new ArrayList<>(path);
            newPath.add(currentZero.get(1) + 1);

            BranchAndBound Y = new BranchAndBound(handler.getY(), newPath, lowerBound);
            matrixUpdate(Y.matrix, Y.record, Y.recordWeight);

            BranchAndBound N = new BranchAndBound(handler.getN(), path, lowerBound);
            matrixUpdate(N.matrix, N.record, N.recordWeight);
        }
    }

    private double findMin(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> minimum, double lowerBound) {
        for (ArrayList<Double> d : matrix) {
            if (Collections.min(d) == Double.POSITIVE_INFINITY) {
                minimum.add(0.0);
            } else {
                minimum.add(Collections.min(d));
            }
            lowerBound += minimum.get(minimum.size() - 1);

            for (int i = 0; i < d.size(); i++) {
                if (d.get(i) != Double.POSITIVE_INFINITY) {
                    d.set(i, d.get(i) - minimum.get(minimum.size() - 1));
                }
            }
        }
        return lowerBound;
    }

    private void transpose(ArrayList<ArrayList<Double>> a) {
        for (int i = 0; i < a.size(); i++) {
            for (int j = i + 1; j < a.size(); j++) {
                double temp = a.get(i).get(j);
                a.get(i).set(j, a.get(j).get(i));
                a.get(j).set(i, temp);
            }
        }
    }

    // finds fine (coefficient) of zero
    public double findCoefficients(ArrayList<ArrayList<Double>> matr, int row, int col) {
        ArrayList<Double> rowElements = new ArrayList<>();
        ArrayList<Double> colElements = new ArrayList<>();

        for (int i = 0; i < matr.size(); i++) {
            if (i != row) {
                colElements.add(matr.get(i).get(col));
            } else {
                for (int j = 0; j < matr.get(i).size(); j++) {
                    if (j != col) {
                        rowElements.add(matr.get(i).get(j));
                    }
                }
            }
        }
        return Collections.min(rowElements) + Collections.min(colElements);
    }

    public ArrayList<ArrayList<Integer>> findZeros(ArrayList<ArrayList<Double>> matr, ArrayList<Integer> Path) {
        ArrayList<ArrayList<Integer>> zeros = new ArrayList<>();
        ArrayList<Double> coeffList = new ArrayList<>();
        for (int i = 0; i < matr.size(); i++) {
            for (int j = 0; j < matr.size(); j++) {
                if (matr.get(i).get(j) == 0 && i == Path.get(Path.size() - 1) - 1) {
                    zeros.add(new ArrayList<>(Arrays.asList(i, j)));
                    coeffList.add(findCoefficients(matr, i, j));
                }
            }
        }
        if (zeros.size() != 0) {
            findMaxCoeff(zeros, coeffList);
        }
        return zeros;
    }

    public void findMaxCoeff(ArrayList<ArrayList<Integer>> zeros, ArrayList<Double> coeffList) {
        double maxCoeff = Collections.max(coeffList);
        for (int i = 0; i < coeffList.size(); i++) {
            if (coeffList.get(i) != maxCoeff) {
                coeffList.remove(i);
                zeros.remove(i);
                i--;
            }
        }
    }
}
