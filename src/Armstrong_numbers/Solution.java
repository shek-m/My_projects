package Armstrong_numbers;

import java.util.*;

/*
Алгоритмы-числа
*/

public class Solution {

    public static long[] getNumbers(long N) {
        long[] result = null;
        TreeSet<Long> finalSet = new TreeSet<>(); //здесь будут храниться конечные числа

        // создадим и заполним двойной массив
        long[][] table = new long[10][20];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = fastPaw(i, j);
            }
        }

        // создаем массив, длинной в порядок числа N, заполняем его 9-ми
        int[] target = new int[numbLength(N)];
        Arrays.fill(target, 9);

        // очередная попытка написать рабочий цикл

        for (int i = 0; i < target.length; i++) {

            for (int j = 8; j >= 0 ; j--) {
                // копируем значение во все предыдущие ячейки
                for (int k = i; k >= 0; k--)
                    target[k] = j;

                // вместо этого блока видимо долна быть рекурсия, которую я не совсем понимаю как можно реализовать
                if (i != 0) {
                    for (int m = target[0]; m >= 0; m--) {
                        target[0] = m;
                        System.out.println(Arrays.toString(target));
                    }
                }
                else
                    System.out.println(Arrays.toString(target));

            }
            System.out.println("переключаем на " + (i+1));
        }


        return new long[0];

    }



    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(1000)));
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);

        a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(1000000)));
        b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);
    }

    public static int numbLength(long N){
        long p = 10;
        for (int i = 1; i < 19; i++) {
            if(N<p)
                return i;
            p*=10;
        }
        return 19;
    }

    public static long fastPaw(int a, int n){
        if (n==0){
            return 1;}
        if (n%2==1){
            return fastPaw(a, n-1) * a;}
        else{
            long b = fastPaw(a, n/2);
            return b*b;
        }
    }
}