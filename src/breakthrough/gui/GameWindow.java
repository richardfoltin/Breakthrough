package breakthrough.gui;

import breakthrough.model.Player;
import breakthrough.model.Field;
import breakthrough.model.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public final class GameWindow extends BaseWindow {

    private final int size;
    private final Model model;
    private final JLabel footerLabel;
    private final Timer footerTimer;
    private final ArrayList<Field> fields;
    
    /**
     * Konstruktor a játékablakhoz.
     * Létrehozza a játékhoz tartozó paneleket és gombokat.
     * Továbbá létrehoz egy Timert ami majd a hibaüzenetek kiírásához lesz szükséges.
     * 
     * @param size a játék size x size-as méretben jön létre
     */
    public GameWindow(Model model) {
        
        this.model = model;
        this.size = model.getSize();
        this.fields = model.getFields();
        
        this.footerTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.this.updateFooterText();
            }
        });
        
        footerTimer.setRepeats(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));
        mainPanel.setPreferredSize(new Dimension(size*50,size*50));
        
        for (Field field : fields) mainPanel.add(field);
        
        JPanel footerPanel = new JPanel();
        footerLabel = new JLabel();
        footerPanel.add(footerLabel);
        footerPanel.setPreferredSize(new Dimension(300,30));
        
        GameMenu menu = new GameMenu(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(menu, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
        
        updateFooterText();
        pack();
        centerWindow();
    }
    
    /**
     * Az ablak alján lévő státuszüzeneteket (milyen lépés következik) frissíti.
     */
    public void updateFooterText() {
        footerLabel.setForeground(Color.black);
        
        if (model.selectionComes()) footerLabel.setText(String.format("Select a(n) %s unit!",model.getActualPlayer().getName()));
        else footerLabel.setText(String.format("Move the %s unit to the available fields!", model.getActualPlayer().getName()));

    }
    
    /**
     * Ha nem lehet kijelölni a választott mezőt, az ablak alján lévő státuszüzenetbe hibajelzést ír,
     * és elindítja a timert.
     */
    public void cantSelectThis() {
        footerLabel.setForeground(Color.red);
        footerLabel.setText("Invalid selection!");
        footerTimer.restart();
    }
    
    /**
     * Ha nem lehet a választott mezőre lépni, az ablak alján lévő státuszüzenetbe hibajelzést ír,
     * és elindítja a timert.
     */
    public void cantMoveHere() {
        footerLabel.setForeground(Color.red);
        footerLabel.setText("Invalid target!");
        footerTimer.restart();
    }
    
    /**
     * Megjeleníti a játék vége ablakot, és kiírja ki a nyertes.
     * 
     * @param winner a játék nyertese
     */
    public void showGameOverMessage(Player winner) {
        Object[] options = {"New Game","Main Menu"};
        int result = JOptionPane.showOptionDialog(this,
                                     winner.getName() + " player has won the game!",
                                     "Winner",
                                     JOptionPane.YES_NO_OPTION,
                                     JOptionPane.PLAIN_MESSAGE,
                                     winner.getIcon(),
                                     options,
                                     null);
                    
        if (result == JOptionPane.YES_OPTION) model.resetGame();
        else doUponExit();              
    }
    
    @Override
    protected void doUponExit() {
        MenuWindow.getInstance().remove(this);
        super.doUponExit();
    }
 
    public Model getModel() {return model;}   
}
