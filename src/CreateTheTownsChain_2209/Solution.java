package CreateTheTownsChain_2209;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    /*
Составить цепочку слов
*/


        public static void main(String[] args) throws IOException {
            //...
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader fileReader = new BufferedReader(new FileReader(reader.readLine()));
            StringBuilder fileContent = new StringBuilder();
            while (fileReader.ready()){
                fileContent.append(fileReader.readLine()).append(" ");
            }
            String[] words = fileContent.toString().split(" ");

            StringBuilder result = getLine(words);
            System.out.println(result.toString());
        }

        public static StringBuilder getLine(String... words) {
            StringBuilder result = new StringBuilder();
            List<String> list = new ArrayList<>();
            String[] wordsClone = Arrays.copyOf(words, words.length);

//        System.out.println(Arrays.toString(wordsClone));

            for (int p = 0; p < words.length; p++) {
                int itterationCount = 0;
                boolean isInfinite = false;

                while (list.size() < wordsClone.length && !isInfinite) {
//                System.out.println("новая иттерация");
//                for (int i = 0; i < list.size(); i++) {
//                    System.out.print(list.get(i) + " ");
//                }
//                System.out.println("wordsClone: " + Arrays.toString(wordsClone));
//                System.out.println();
//                System.out.println(wordsClone.length + " и " + list.size());

                    if (list.size() == 0) {
                        list.add(wordsClone[p]);
                        wordsClone[p] = String.format("/%s/", wordsClone[p]);
                    }
                    for (int i = 0; i < wordsClone.length; i++) {
                        String lastWordFromList = list.get(list.size() - 1);
                        if (lastWordFromList.substring(lastWordFromList.length() - 1).equalsIgnoreCase(wordsClone[i].substring(0, 1))) {
                            list.add(wordsClone[i]);
                            wordsClone[i] = String.format("/%s/", wordsClone[i]);
                        }
                    }
                    itterationCount++;
                    //проверка на бесконечный цикл
                    if (itterationCount > wordsClone.length+5){
//                    System.out.println("зашли в первый уровень");
                        isInfinite = true;
                        list.clear();
                        wordsClone = Arrays.copyOf(words, words.length);
                    }
                }
                if (list.size()== words.length){
//                System.out.println("зашли во второй уровень");
                    break;}
            }
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        };

            for (String x: list) {
                if(result.length()!=0)
                    result.append(" ");
                result.append(x);
            }

            return result;
        }
    }

