package htmlEditor;

import htmlEditor.listeners.FrameListener;
import htmlEditor.listeners.TabbedPaneChangeListener;
import htmlEditor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    private UndoManager undoManager = new UndoManager();

    public UndoListener getUndoListener() {
        return undoListener;
    }

    private UndoListener undoListener = new UndoListener(undoManager);

    public View(){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            ExceptionHandler.log(e);
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init(){
        initGui();
        Container contentPane = this.getContentPane();
        this.addWindowListener(new FrameListener(this));
        this.setVisible(true);
    }

    public void exit(){
        controller.exit();
    }

    public boolean canUndo(){
        return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    public void undo(){
        try {
            undoManager.undo();
        } catch (CannotUndoException e) {
            ExceptionHandler.log(e);
        }
    }

    public void redo(){
        try {
            undoManager.redo();
        } catch (CannotRedoException e) {
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    public boolean isHtmlTabSelected(){
        if(tabbedPane.getSelectedIndex() == 0)
            return true;
        else
            return false;
    }

    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(getContentPane(), "Программа была создана топ чуваком Мишей. All rights reserved. 2021", "About", JOptionPane.INFORMATION_MESSAGE);

    }

    public void initMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar);
        this.getContentPane().add(jMenuBar, BorderLayout.NORTH);

    }

    public void initEditor(){
        htmlTextPane.setContentType("text/html");       //устанавливаем тип контента даного JEditorPane
        JScrollPane jScrollPane1 = new JScrollPane(htmlTextPane);        //создаем панель для скрола, добавляем туда наш JEditorPane
        tabbedPane.add("HTML", jScrollPane1);

        JScrollPane jScrollPane2 = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст", jScrollPane2);

        tabbedPane.setPreferredSize(new Dimension(500, 500));
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));

        Container cp = this.getContentPane();
        cp.add(tabbedPane, BorderLayout.CENTER);   //ложим в основное окно панель cо складками, по центру

    }

    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }

    public void selectedTabChanged(){
        if (tabbedPane.getSelectedIndex() == 0) {
            String text = plainTextPane.getText();
            controller.setPlainText(text);
        }
        else
            plainTextPane.setText(controller.getPlainText());
        resetUndo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action){
            case "Новый": controller.createNewDocument();
                break;
            case "Открыть": controller.openDocument();
                break;
            case "Сохранить": controller.saveDocument();
                break;
            case "Сохранить как...": controller.saveDocumentAs();
                break;
            case "Выход": controller.exit();
                break;
            case "О программе": this.showAbout();
                break;
        }
    }
}