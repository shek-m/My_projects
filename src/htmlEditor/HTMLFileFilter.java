package htmlEditor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.getName().endsWith(".html") || f.getName().endsWith(".htm") || f.getName().endsWith(".HTML") || f.getName().endsWith(".HTM") || f.isDirectory();
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}