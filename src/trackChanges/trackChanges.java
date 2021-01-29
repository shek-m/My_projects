package trackChanges;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
Отслеживаем изменения
*/

public class trackChanges {
    public static List<LineItem> lines = new ArrayList<LineItem>();

    public static void main(String[] args) throws IOException {
        List<LineItem> file1 = new ArrayList<>();
        List<LineItem> file2 = new ArrayList<>();
        String p, z = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader fileReader1 = new BufferedReader(new FileReader(reader.readLine()));
        BufferedReader fileReader2 = new BufferedReader(new FileReader(reader.readLine()));

        while((p = fileReader1.readLine()) != null){
            if (!p.equals("")) {
                file1.add(new LineItem(Type.SAME, p));
            }
        }
        while((z = fileReader2.readLine()) != null){
            if (!z.equals("")) {
                file2.add(new LineItem(Type.SAME, z));
            }
        }
        // уравниваем длинну массивов
        if(file1.size()>file2.size()) {
            while (file2.size() != file1.size())
                file2.add(new LineItem(Type.SAME, ""));
        }
        else if(file1.size()<file2.size()) {
            while (file2.size() != file1.size())
                file1.add(new LineItem(Type.SAME, ""));
        }


        for (LineItem k: file1) {
            if (file1.indexOf(k) != file1.size()-1 && !k.line.equals("")) {
                if(k.line.equals(file2.get(file1.indexOf(k)).line))
                    lines.add(k);
                else if (k.line.equals(file2.get(file1.indexOf(k)+1).line)) {
                    lines.add(new LineItem(Type.ADDED, file2.get(file1.indexOf(k)).line));
                    lines.add(k);
                    file2.remove(file2.get(file1.indexOf(k)));
                }
                else {
                    lines.add(new LineItem(Type.REMOVED, k.line));
                    file2.add(file1.indexOf(k), k);
                }
            }
            else if(file1.indexOf(k) == file1.size()-1 && !k.line.equals("")){
                file2.add(new LineItem(Type.SAME, ""));
                if(k.line.equals(file2.get(file1.indexOf(k)).line))
                    lines.add(k);
                else if(file2.get(file1.indexOf(k)).line.equals(""))
                    lines.add(new LineItem(Type.REMOVED, k.line));
                else if (k.line.equals(file2.get(file1.indexOf(k)+1).line)) {
                    System.out.println("зш");
                    lines.add(new LineItem(Type.ADDED, file2.get(file1.indexOf(k)).line));
                    lines.add(k);
                    file2.remove(file2.get(file1.indexOf(k)));
                }
                if (!file2.get(file1.indexOf(k)+1).line.equals("") && !file2.get(file1.indexOf(k)+1).line.equals(k.line)){
                    lines.add(new LineItem(Type.ADDED, file2.get(file1.indexOf(k)+1).line));}
            }
            else if (k.line.equals("")) {
                if (file1.size() > file2.size()) {
                    while (file2.size() != file1.size())
                        file2.add(new LineItem(Type.SAME, ""));
                }
                if (!file2.get(file1.indexOf(k)).line.equals("")) {
                    lines.add(new LineItem(Type.ADDED, file2.get(file1.indexOf(k)).line));
                }
            }
        }
        reader.close();
        fileReader1.close();
        fileReader2.close();


        for (LineItem x:  lines) {
            System.out.println(x.type + "\t" + x.line);
        }

    }


    public static enum Type {
        ADDED,        //добавлена новая строка
        REMOVED,      //удалена строка
        SAME          //без изменений
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }
    }
}