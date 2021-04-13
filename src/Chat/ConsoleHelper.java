package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString(){
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("Произошла ошибка при попытке ввода текста. Попробуйте ещё раз.");
            line = readString();
        }
        return line;
    }

    public static int readInt(){
        int numb;
        try{
            numb = Integer.parseInt(readString());
        } catch (NumberFormatException e){
            System.out.println("Произошла ошибка при попытке ввода числа. Попробуйте ещё раз.");
            numb = readInt();
        }
        return numb;
    }
}