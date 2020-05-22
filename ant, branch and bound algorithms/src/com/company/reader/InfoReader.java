package com.company.reader;

import java.io.InputStream;
import java.util.Scanner;

public class InfoReader {
    public static int MIN_VERTEX = 3;
    public static int MAX_VERTEX = 20;
    public static final String REGEX_NATURAL = "[1-9]\\d?";

    public int intReader(InputStream input) {
        Scanner scanner = new Scanner(input);
        String line = scanner.nextLine();
        line = line.trim();
        while (!line.matches(REGEX_NATURAL)) {
            System.out.println("Input natural number!");
            line = scanner.nextLine();
            line = line.trim();
        }
        return Integer.parseInt(line);
    }

    public int readVertex() {
        int n = 0;
        while (n < MIN_VERTEX || n > MAX_VERTEX) {
            System.out.println("Input amount of vertices between 3 and 20: ");
            n = intReader(System.in);
        }
        return n;
    }
}
