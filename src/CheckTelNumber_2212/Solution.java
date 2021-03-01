package CheckTelNumber_2212;
/*
Проверка номера телефона
*/

import java.util.ArrayList;
import java.util.List;

public class Solution {

        public static boolean checkTelNumber(String telNumber) {
            if (telNumber==null || telNumber.equals(""))
                return false;

            if (telNumber.startsWith("+") && telNumber.matches("^([^\\d]?\\d[^\\d]?){12}$")) {
                System.out.println("В номере 12 цифр, и начинается с +");
                if (!telNumber.matches("^.{2,4}(\\(\\d{3}\\))?([^-()]+-[^-()]+){0,2}(\\d+)?\\d$")){
                    System.out.println("номер имеет не 3 цифры в скобках/имеет больше 2 тире/занакч не на цифру/имеет скобки правее тире...");
                    return false;}
                if (!telNumber.matches("^[^a-zA-Zа-яА-Я]+$")){
                    System.out.println("В номере есть буквы");
                    return false;}

                return true;
            } else if ((telNumber.substring(0, 1).matches("\\d") || telNumber.startsWith("(")) && telNumber.matches("^([^\\d]?\\d[^\\d]?){10}$")) {
                System.out.println("зашли в 1 else");
                if (!telNumber.matches("^(\\(\\d{3}\\))?([^-()]+-[^-()]+){0,2}(\\d+)?\\d$")){
                    System.out.println("номер имеет не 3 цифры в скобках/имеет больше 2 тире/занакч не на цифру/имеет скобки правее тире...");
                    return false;}
                if (!telNumber.matches("^[^a-zA-Zа-яА-Я]+$")){
                    System.out.println("В номере есть буквы");
                    return false;}

                System.out.println("Все отлично, номер корректный");
                return true;
            }
            System.out.println("С номером что-то совсем не так..");
            return false;
        }

        public static void main(String[] args) {
            List<String> list = new ArrayList<>();
            list.add("+380501234567");
            list.add("+38(050)1234567");
            list.add("+38050123-45-67");
            list.add("050123-4567");
            list.add("+38)050(1234567");
            list.add("+38(050)1-23-45-6-7");
            list.add("050ххх4567");
            list.add("050123456");
            list.add("(0)501234567");

            for (String x:   list ) {
                System.out.println(x + " - " + checkTelNumber(x));
            }
        }

    }
