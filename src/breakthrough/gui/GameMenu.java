package breakthrough.gui;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class GameMenu extends JMenuBar {

    private final JMenu newGameMenu;
    private final JMenuItem loadMenu;
    private final JMenuItem saveMenu;
    private final JMenuItem exitSaveMenu;
    private final JMenuItem exitMenu;
    private final JMenuItem restartGameMenu;
    
    private static final int MENUITEM_WIDTH = 140;
    private static final int MENUITEM_HEIGHT = 25;
    
    /**
     * A játék menüjét létrehozó konstruktor
     * 
     * @param window a játék ablaka
     */
    public GameMenu(GameWindow window) {

        JMenu menuFile = new JMenu("File");  
        menuFile.setMnemonic('F');
        
        loadMenu = new JMenuItem();
        loadMenu.addActionListener(GameAction.loadAction(window));
        loadMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
        loadMenu.setText("Load Game...");
        loadMenu.setMnemonic('L');
        loadMenu.setPreferredSize(new Dimension(MENUITEM_WIDTH,MENUITEM_HEIGHT));
        menuFile.add(loadMenu);
        
        saveMenu = new JMenuItem();
        saveMenu.addActionListener(GameAction.saveAction(window));
        saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        saveMenu.setText("Save Game...");
        saveMenu.setMnemonic('S');
        saveMenu.setPreferredSize(new Dimension(MENUITEM_WIDTH,MENUITEM_HEIGHT));
        menuFile.add(saveMenu);

        menuFile.addSeparator();
        
        exitSaveMenu = new JMenuItem();
        exitSaveMenu.addActionListener(GameAction.exitAndSaveAction(window));
        exitSaveMenu.setText("Exit and Save...");
        exitSaveMenu.setMnemonic('E');    
        exitSaveMenu.setPreferredSize(new Dimension(MENUITEM_WIDTH,MENUITEM_HEIGHT));
        menuFile.add(exitSaveMenu);  
        
        
        exitMenu = new JMenuItem();
        exitMenu.addActionListener(GameAction.exitAction(window));
        exitMenu.setText("Exit Without Saving");
        exitMenu.setMnemonic('x');    
        exitMenu.setPreferredSize(new Dimension(MENUITEM_WIDTH,MENUITEM_HEIGHT));
        menuFile.add(exitMenu);
        
        JMenu menuGame = new JMenu("Game");  
        menuGame.setMnemonic('G');      
        
        newGameMenu = new JMenu();
        newGameMenu.setText("New Game");
        newGameMenu.setMnemonic('N');
        newGameMenu.setPreferredSize(new Dimension(MENUITEM_WIDTH,MENUITEM_HEIGHT));
        menuGame.add(newGameMenu);
        
        int[] sizes = {6,8,10};
        for (int boardSize : sizes){
            JMenuItem newGameItem = new JMenuItem();
            newGameItem.setText(String.format("%d x %d",boardSize,boardSize));
            newGameItem.addActionListener(GameAction.newGameAction(boardSize));
            newGameMenu.add(newGameItem);
        }

        restartGameMenu = new JMenuItem();
        restartGameMenu.addActionListener(GameAction.restartGameAction(window.getModel()));
        restartGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
        restartGameMenu.setText("Restart Game");
        restartGameMenu.setMnemonic('N');
        restartGameMenu.setPreferredSize(new Dimension(MENUITEM_WIDTH,MENUITEM_HEIGHT));
        menuGame.add(restartGameMenu);
        
        add(menuFile);
        add(menuGame);
             
    }

}
