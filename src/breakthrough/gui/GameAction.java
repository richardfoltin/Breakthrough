package breakthrough.gui;

import breakthrough.model.Player;
import breakthrough.model.Field;
import breakthrough.model.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GameAction {
    
    public static class IllegalFileException extends Exception {}
    
    /**
     * A játék újraindításához szükséges eseménykezelőt hoz létre
     * 
     * @param model a játék modelje
     * @return a létrehozott eseménykezelő
     */
    public static final ActionListener restartGameAction(Model model) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 model.resetGame();
            }
        };
    }

    /**
     * A játék indításához szükséges eseménykezelőt hoz létre
     * 
     * @param size az esemény elindulásakor induló játék mérete
     * @return a létrehozott eseménykezelő
     */ 
    public static final ActionListener newGameAction(final int size) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ArrayList<Field> fields = new ArrayList<>();
                    Model model = new Model(size, fields, Player.HUMAN);
                    for (int i = 0; i < size; ++i) {
                        for (int j = 0; j < size; ++j) {
                            Field field = new Field(model, i, j);
                            fields.add(field);
                        }
                    }
                    model.startGameWindow();
            }
        };
    }
    
    /**
     * A játék betöltéséhez szükséges eseménykezelőt hoz létre
     * 
     * @param window a belöltést indító ablak
     * @return a létrehozott eseménykezelő
     */
    public static final ActionListener loadAction(BaseWindow window) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Load Game");
                chooser.setApproveButtonText("Load");
                chooser.setApproveButtonMnemonic('L');
                File chosenFile = (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
                if (chosenFile != null) {
                    try (Scanner sc = new Scanner(chosenFile)){
                        ArrayList<Field> fields = new ArrayList<>();
                        if (!sc.hasNextInt()) throw new IllegalFileException();
                        int size = sc.nextInt();
                        if (size > 30) throw new IllegalFileException();
                        if (!sc.hasNext()) throw new IllegalFileException();
                        Player startingPlayer = Player.fromCode(sc.next()); 
                        Model model = new Model(size,fields,startingPlayer);
                        
                        int row = 0;
                        int column = 0;
                        while(sc.hasNext()) {
                            if (column == size) {
                                column = 0;
                                row++;
                            }
                            String playerCode = sc.next();
                            if (playerCode.length() != 1) throw new IllegalFileException();
                            Player player = Player.fromCode(playerCode);      
                            Field field = new Field(model, row, column, player);
                            fields.add(field);
                            column++;
                        }
                        
                        if (row >= size) throw new IllegalFileException();
                        model.startGameWindow();

                    } catch (IllegalFileException ex) {
                        JOptionPane.showMessageDialog(window, "Not proper Breakthrough saved file.");
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(window, "File not found!");
                    }
                }
            }
        };
    }
    
    /**
     * A játék mentéséhez szükséges eseménykezelőt hoz létre
     * 
     * @param game a játék ablaka
     * @return a létrehozott eseménykezelő
     */
    public static final ActionListener saveAction(GameWindow game) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save(game,false);
            }
        };
    }

    /**
     * A játék mentéséhez és kilépéshez szükséges eseménykezelőt hoz létre
     * 
     * @param game a játék ablaka
     * @return a létrehozott eseménykezelő
     */
    public static final ActionListener exitAndSaveAction(GameWindow game) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save(game,true);
                
            }
        };
    }
    
    /**
     * A játékból való kilépéshez szükséges eseménykezelőt hoz létre
     * 
     * @param game a játék ablaka
     * @return a létrehozott eseménykezelő
     */
    public static final ActionListener exitAction(GameWindow game) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showExitConfirmation();
            }
        };
    }
    
    /**
     * A mentéshez szükséges metódus. Feldob egy file-választó ablakot 
     * és a kiválasztott file-ba elmenti a játékot
     * 
     * @param game a játék ablaka
     * @param withExit true, ha a mentés után ki is kell lépni a játékból
     */
    private static final void save(GameWindow game, boolean withExit){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Game");
        chooser.setApproveButtonText("Save");
        chooser.setApproveButtonMnemonic('S');
        File chosenFile = (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
        if (chosenFile != null) {
            try (PrintWriter pw = new PrintWriter(chosenFile)){
                Model model = game.getModel();
                ArrayList<Field> fields = model.getFields();

                pw.println(model.getSize());
                pw.println(model.getActualPlayer());

                for (Field field : fields) {
                    Player player = field.getPlayer();
                    if (player == null) pw.print("0");
                    else pw.print(player);
                    pw.print(" ");
                }

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(game, "File not found!");
            }
        }  
        
        if (withExit) game.doUponExit();
    }
}
