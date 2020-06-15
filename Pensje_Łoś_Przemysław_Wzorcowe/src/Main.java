/*************************************************************************
 *                                                                       *
 *   Zadanie:           Pensje                                           *
 *   Autor:             Przemysław Loś                              *
 *                                                                       *
 *************************************************************************/

import java.util.Scanner;

public class Main {

    private static final int MAXN = 1000005;
    int[] principals = new int[MAXN];
    int[] salaries = new int[MAXN];
    int[] usedSalaries = new int[MAXN];
    int[] numbersOfSubordinates = new int[MAXN];
    int[] subordinates = new int[MAXN];
    int[] queue = new int[MAXN];
    int[] ranks = new int[MAXN];
    int numberOfEmployees = 0;
    int index_1 = 0;
    int index_2 = 0;

    void countSubordinates() {
        for (int i = 0; i < numberOfEmployees; ++i) {
            ranks[i] = 0;
        }
        for (int i = 0; i < numberOfEmployees; ++i)
            ranks[principals[i]] = ranks[principals[i]] + 1; //1
        for (int i = 0; i < numberOfEmployees; i++)
            if (ranks[i] == 0) {
                queue[index_1++] = i;
            }
        while (index_2 < index_1) {
            int ancillaryVariable = queue[index_2++];
            int principal = principals[ancillaryVariable];
            if (salaries[ancillaryVariable] == 0) {
                --ranks[principal];
                if (ranks[principal] == 0)
                    queue[index_1++] = principal;
                numbersOfSubordinates[principal] += numbersOfSubordinates[ancillaryVariable] + 1;
            }
        }
    }

    void markUsedSalaries() {
        for (int i = 1; i < numberOfEmployees; ++i) //2
            if (salaries[i] != 0)
                usedSalaries[salaries[i]] = i;
            else if (subordinates[principals[i]] == 0)
                subordinates[principals[i]] = i;
            else subordinates[principals[i]] = -1;

    }


    void algorytm() {
        int i = 0;
        int numberOfFree = 0;
        int numberOfPossible = 0;
        while (i < numberOfEmployees - 1) {
            while (i < numberOfEmployees - 1 && usedSalaries[i + 1] == 0) {
                ++i;
                numberOfFree++;
                numberOfPossible++;
            }
            while (i < numberOfEmployees - 1 && usedSalaries[i + 1] != 0) {
                ++i;
                int ancillaryVariable = usedSalaries[i];
                int l = i;
                numberOfFree -= numbersOfSubordinates[ancillaryVariable];
                if (numberOfFree == 0) {
                    while (numberOfPossible > 0 && subordinates[ancillaryVariable] > 0) {
                        numberOfPossible--;
                        while (usedSalaries[l] > 0) --l;
                        ancillaryVariable = subordinates[ancillaryVariable];
                        salaries[ancillaryVariable] = l;
                        usedSalaries[l] = ancillaryVariable;
                    }
                    numberOfPossible = 0;
                }
                if (numbersOfSubordinates[ancillaryVariable] != 0)
                    numberOfPossible = 0;
            }
        }
        for (int j = 1; j < numberOfEmployees; j++)
            System.out.println(salaries[j]);
    }

    public static void main(String[] args) {

        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        main.numberOfEmployees = scanner.nextInt();
        for (int i = 1; i <= main.numberOfEmployees; i++) {
            main.principals[i] = scanner.nextInt();
            main.salaries[i] = scanner.nextInt();
            if (main.principals[i] == i) main.salaries[i] = main.numberOfEmployees;
            if (main.salaries[i] != 0) main.principals[i] = main.numberOfEmployees + 1;
        }
        main.numberOfEmployees++;
        main.principals[main.numberOfEmployees] = main.numberOfEmployees;
        main.salaries[main.numberOfEmployees] = main.numberOfEmployees;

        main.countSubordinates();
        main.markUsedSalaries();
        main.algorytm();
    }
}
