package Lab2;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {
    float[][] matrix;
    int jWithMaxElement;
    float[] array;

    Lab2(int n) {
        float[][] matrix = new float[n][n];
        array = new float[n];
        Scanner nextFloat = new Scanner(System.in);
        float maxNumber = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; ) {
                String str = nextFloat.nextLine();
                Pattern findNumber = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+");
                try {
                    Matcher m = findNumber.matcher(str);
                    while (m.find()) {
                        try {
                            float number = (float) Math.round(Float.parseFloat(m.group()) * 100) / 100;
                            if (maxNumber < number && i % 2 != 0 && j % 2 != 0) {
                                maxNumber = number;
                                jWithMaxElement = j;
                            }
                            matrix[i][j] = number;
                            System.out.printf("[%d][%d] -> %f\n", i, j, matrix[i][j]);
                            if (j < matrix.length) {
                                j++;
                            } else {
                                j = 0;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    if (str.isEmpty()) {
                        System.err.println("Empty String");
                    } else System.err.println("Float format exception");

                }
            }
        }
        this.matrix = matrix;
        System.out.printf("Max number %f\n", maxNumber);
    }

    public float[] getArray() {
        for (int i = 0; i < matrix.length; i++) {
            array[i] = matrix[i][jWithMaxElement];
            System.out.printf("[%d][%d] -> %f\n", i, jWithMaxElement, array[i]);
        }
        return array;
    }

    public float getRandomFloat(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }
}
