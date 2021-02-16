package Rectangle_search_2026;


/*
Алгоритмы-прямоугольники
*/

import java.util.*;

public class Solution {
    public static int finalCount = 0;
    public static List<String> list = new ArrayList<>();
    public static void main(String[] args) {
        byte[][] a1 = new byte[][]{
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 1}
        };
        byte[][] a2 = new byte[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 1}
        };

        int count1 = getRectangleCount(a1);
        System.out.println("count = " + count1 + ". Должно быть 2");
        int count2 = getRectangleCount(a2);
        System.out.println("count = " + count2 + ". Должно быть 4");
    }

    public static void start(int i, int j){
        list.add(String.format("start|%d|%d", i,j));
    }
    public static void finish(int i, int j){
        list.add(String.format("finish|%d|%d", i,j));
    }


    public static int getRectangleCount(byte[][] a) {
        // номер ячейки - индекс первого массива, число в ячейке - номер второго массива
        byte[][] initArr =a;
        finalCount=0;
        list.clear();

        int lenth = a.length;


        int sideLength = 0;

        for (int i = 0; i < lenth; i++) {               // i - индекс внешнего массива, j - индекс внутреннего массива
            for (int j = 0; j < lenth; j++) {

                if (j==0){
                    if (a[i][j]==1){
                        start(i,j);
                        if (a[i][j+1]!=1)
                            finish(i,j);
                    }
                }
                else if (j !=lenth-1){
                    if (a[i][j]==1 && a[i][j-1]!=1)
                        start(i,j);
                    if (a[i][j]==1 && a[i][j+1]!=1)
                        finish(i,j);
                }
                else{
                    if (a[i][j]==1 && a[i][j-1]!=1) {
                        start(i,j);
                        finish(i,j);
                    }
                    if(a[i][j]==1 && a[i][j-1]==1)
                        finish(i,j);
                }
            }
        }

        //просто показываем, что какие элементы добавились
        System.out.println("Старты и финиши:");
        for (String x: list) {
            System.out.println(x);
        }

        //начинаем перебирать и парсить список со строками старт и финиш по оси х

        for (int i = 0; i < list.size(); i+=2) {
            String[] startArr = list.get(i).split("\\|");
            int startPseudoI = Integer.parseInt(startArr[1]);
            int startPseudoJ = Integer.parseInt(startArr[2]);
            String[] finishArr = list.get(i + 1).split("\\|");
            int finishPseudoI = Integer.parseInt(finishArr[1]);
            int finishPseudoJ = Integer.parseInt(finishArr[2]);

            int heightFinishI = 0;
            int elementCounter = 0;

            // теперь нам нужно проверить высоту этого прямоугольника (по оси y).
            for (int k = startPseudoI; k < lenth; k++) {
                if (k != lenth - 1) {
                    if (a[k + 1][startPseudoJ] == 0) {
                        heightFinishI = k;
                        break;
                    }
                } else {
                    if (a[k][startPseudoJ] == 1)
                        heightFinishI = k;
                }
            }
            System.out.println("элемент по высоте заканчивается: " + heightFinishI);

            // просто показываем, какме координаты по оси х, и по оси y
            System.out.println(String.format("Current rect #%d coord: OX - from i:%d; j:%d --> to i:%d; j:%d", i + 1, startPseudoI, startPseudoJ, finishPseudoI, finishPseudoJ));
            System.out.println(String.format("Current rect #%d coord: OY - from i:%d; j:%d --> to i:%d; j:%d", i + 1, startPseudoI, startPseudoJ, heightFinishI, startPseudoJ));

            // теперь будем ходить по этому квадрату, и смотреть, чтобы все элементы были равны единице
            for (int l = startPseudoI; l <= heightFinishI; l++) {
                for (int m = startPseudoJ; m <= finishPseudoJ; m++) {
                    if (a[l][m] == 1) {
                        elementCounter++;
                        a[l][m] = 0;
                    }
                }
            }

            int square = ((finishPseudoJ + 1) - startPseudoJ) * ((heightFinishI + 1) - startPseudoI);

            if (elementCounter == square) {
                //проверка, а не прикасается ли к нему чего со всяких сторон. СДЕЛАТЬ!!
                finalCount++;
            }

            System.out.println(elementCounter + " vs square" + square);

            for (int j = 0; j < initArr.length; j++) {
                for (int k = 0; k < initArr[j].length; k++) {
                    System.out.print(initArr[j][k] + " ");
                    if (k == initArr[j].length - 1) {
                        System.out.println(initArr[j][k]);

                    }
                }
            }

        }


        return finalCount;
    }


}