package Crossword_2027;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
Кроссворд
*/

public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'x', 'x'},
                {'x', 'x'}
        };
        List<Word> finp = detectAllWords(crossword, "x", "xx");
        for (int i = 0; i < finp.size(); i++) {
            System.out.println(finp.get(i));
        }
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }


    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> result = new ArrayList<>();
        List<String> starts = new ArrayList<>();
        List<String> finishes = new ArrayList<>();


        for (String word:  words) {
//            System.out.println(word);


            int OX = crossword[0].length;
            int OY = crossword.length;

//            System.out.println("OX - " + OX + ", OY - " + OY);

            //каждое слово отдельно
            int lenth = word.length();
            char[] letChar = word.toCharArray();
            String[] letters = new String[letChar.length];

            for (int i = 0; i < letters.length; i++) {
                letters[i] = String.valueOf(letChar[i]);
            }

//            for (int i = 0; i < letters.length; i++) {      // массив с буквами проверка
//                System.out.println(letters[i]);
//            }
//            System.out.println("lenth- " + lenth);


            //заплоняем наннімі списки
            for (int i = 0; i < OY; i++) {
                for (int j = 0; j < OX; j++) {
//                    System.out.println((char)crossword[i][j]+"");
                    if (String.valueOf((char) crossword[i][j]).equals(letters[0])){
                        starts.add(String.format("%d|%d", i,j));
                    }
                    if (String.valueOf((char) crossword[i][j]).equals(letters[lenth-1])){
                        finishes.add(String.format("%d|%d", i,j));}
                }
            }

/*            System.out.println("Старты: " + starts.size() + finishes.size());
            for (int i = 0; i < starts.size(); i++) {
                System.out.println(starts.get(i));
            }
            System.out.println("Финиши:");
            for (int i = 0; i < finishes.size(); i++) {
                System.out.println(finishes.get(i));
            }*/

            //по горизонтали
            for (String start:  starts) {
                String[] starto = start.split("\\|");
                int startY = Integer.parseInt(starto[0]);
                int startX = Integer.parseInt(starto[1]);

                for (String finish:  finishes) {
                    String[] fin = finish.split("\\|");
                    int finY = Integer.parseInt(fin[0]);
                    int finX = Integer.parseInt(fin[1]);


                    //на одном рядке, в норм или обратном порядке
                    if ((finX==startX+lenth-1) && startY == finY){
                        StringBuilder x = new StringBuilder();

                        //на одном рядке
                        for (int i = startX; i <= finX; i++)
                            x.append((char)crossword[startY][i]);

                        if (word.equals(x.toString())){
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX,finY);
                            if (!result.contains(current))
                                result.add(current);                        }

                    }

                    if ((finX==startX-lenth+1) && startY == finY) {
                        StringBuilder x = new StringBuilder();

                        //на одном рядке в обратном порядке
                        for (int i = finX; i <= startX; i++)
                            x.append((char) crossword[startY][i]);

                        if (word.equals(x.reverse().toString())) {
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX, finY);
                            if (!result.contains(current))
                                result.add(current);                        }
                    }

                    // по вертикали в правильном порядке
                    if ((finY==startY+lenth-1) && (startX==finX)){
                        StringBuilder x = new StringBuilder();

                        for (int i = startY; i <= finY; i++)
                            x.append((char) crossword[i][startX]);

                        if (word.equals(x.toString())){
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX,finY);
                            if (!result.contains(current))
                                result.add(current);                        }
                    }
                    // по вертикали в обратном порядке
                    if ((finY==startY-lenth+1) && (startX==finX)){
                        StringBuilder x = new StringBuilder();

                        for (int i = finY; i <= startY; i++)
                            x.append((char) crossword[i][startX]);

                        if (word.equals(x.reverse().toString())) {
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX, finY);
                            if (!result.contains(current))
                                result.add(current);                        }
                    }
                    // по диагонали в нормальном порядке
                    if ((finY==startY+lenth-1) && (finX==startX+lenth-1)){
                        StringBuilder x = new StringBuilder();

                        int temp = startY;
                        for (int i = startX; i <= finX; i++){
                            x.append((char) crossword[temp][i]);
                            temp++;
                        }
                        if (word.equals(x.toString())) {
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX, finY);
                            if (!result.contains(current))
                                result.add(current);                        }
                    }
                    //диагональ в обратном порядке
                    if ((finY==startY-lenth+1) && (finX==startX-lenth+1)){
                        StringBuilder x = new StringBuilder();

                        int temp = finY;
                        for (int i = finX; i <= startX; i++){
                            x.append((char) crossword[temp][i]);
                            temp++;
                        }
                        if (word.equals(x.reverse().toString())) {
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX, finY);
                            if (!result.contains(current))
                                result.add(current);
                        }

                    }
                    //другая диагональ в правильном порядке
                    if ((finY==startY-lenth+1) && (finX==startX+lenth-1)){
                        StringBuilder x = new StringBuilder();

                        int temp = startY;
                        for (int i = startX; i <= finX; i++){
                            x.append((char) crossword[temp][i]);
                            temp--;
                        }
                        if (word.equals(x.toString())) {
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX, finY);
                            if (!result.contains(current))
                                result.add(current);                        }
                    }
                    //другая диагональ в обратном порядке
                    if ((finY==startY+lenth-1) && (finX==startX-lenth+1)){
                        StringBuilder x = new StringBuilder();

                        int temp = finY;
                        for (int i = finX; i <= startX; i++){
                            x.append((char) crossword[temp][i]);
                            temp--;
                        }
                        if (word.equals(x.reverse().toString())) {
                            Word current = new Word(word);
                            current.setStartPoint(startX, startY);
                            current.setEndPoint(finX, finY);
                            if (!result.contains(current))
                                result.add(current);
                        }
                    }

                }

            }
            starts.clear();
            finishes.clear();

        }

        return result;
    }
    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word = (Word) o;
            return startX == word.startX &&
                    startY == word.startY &&
                    endX == word.endX &&
                    endY == word.endY &&
                    Objects.equals(text, word.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, startX, startY, endX, endY);
        }
    }
}