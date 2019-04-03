package breakthrough.gui;

import breakthrough.Breakthrough;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuWindow extends BaseWindow {
    
    private static ArrayList<GameWindow> games;
    private static MenuWindow instance;
    
    /**
     * A játék menüje singleton, ezért csak ezzel a metódussal érhető el.
     * 
     * @return 
     */
    public static MenuWindow getInstance() {
        if (instance == null) return new MenuWindow();
        else return instance;
    }
        
    /**
     * A játék menüjének privát konstruktora.
     * Létrehozza a menü gombjait, feliratait, és képeit.
     */
    private MenuWindow() {
        this.games = new ArrayList<>();
        this.instance = this;
        
        JLabel label = new JLabel("Start Game");
        label.setFont(new Font(label.getFont().getName(),Font.PLAIN,20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton small = createStartButton(6);
        JButton medium = createStartButton(8);
        JButton large = createStartButton(10);
        JButton loadButton = createLoadButton();
        
        JLabel footer = new JLabel("by Csaba Foltin");
        footer.setFont(new Font(label.getFont().getName(),Font.PLAIN,10));
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel titlePane = new JPanel();
        JPanel buttonPane = new JPanel();
        JPanel footerPane = new JPanel();
        JPanel leftPane = new JPanel();
        JPanel rightPane = new JPanel();
        JPanel mainPane = new JPanel();
        
        titlePane.add(label);
        
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.setPreferredSize(new Dimension(220,130));
        buttonPane.add(small);
        buttonPane.add(Box.createRigidArea(new Dimension(0,10)));
        buttonPane.add(medium);
        buttonPane.add(Box.createRigidArea(new Dimension(0,10)));
        buttonPane.add(large);
        buttonPane.add(Box.createRigidArea(new Dimension(0,10)));
        buttonPane.add(loadButton);
        
        footerPane.add(footer);
        
        URL humanUrl = Breakthrough.class.getResource("resources/human.png");
        JLabel leftLabel = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(humanUrl)),JLabel.RIGHT);
        leftLabel.setPreferredSize(new Dimension(130,200));
        leftPane.add(leftLabel);        
        URL orcUrl = Breakthrough.class.getResource("resources/orc.png");
        JLabel rightLabel = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(orcUrl)),JLabel.LEFT);
        rightLabel.setPreferredSize(new Dimension(130,200));
        rightPane.add(rightLabel);
        
        mainPane.setLayout(new BorderLayout());
        mainPane.add(titlePane,BorderLayout.NORTH);
        mainPane.add(buttonPane,BorderLayout.CENTER);
        mainPane.add(footerPane,BorderLayout.SOUTH);
        mainPane.setPreferredSize(new Dimension(220,200));
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPane,BorderLayout.CENTER);
        getContentPane().add(leftPane,BorderLayout.WEST);
        getContentPane().add(rightPane,BorderLayout.EAST);
        
        pack();
        centerWindow();
    }
    
    /**
     * A futó játékokat nyilvántartó listához hozzáad egy játékot
     * 
     * @param game a játék ablaka
     */
    public void addGame(GameWindow game){
        games.add(game);
    }
    
    /**
     * A futó játékokat nyilvántartó listából eltávolít egy játékot
     * 
     * @param game a játék ablaka
     */
    public void removeGame(GameWindow game){
        games.remove(game);
    }
    
    /**
     * A játék indításához szükséges gombot hozza létre.
     * 
     * @param size a gomb kattintásával induló játék mérete
     * @return a létrehozott gomb
     */
    private JButton createStartButton(int size) {
        JButton button = new JButton();
        button.setText(String.format("%d x %d",size,size));
        button.addActionListener(GameAction.newGameAction(size));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  
        button.setMaximumSize(new Dimension(150,30));
        return button;
    }
    
    /**
     * A játék betöltéséhez szükséges gombot hoz létre.
     * 
     * @return a létrehozott gomb
     */
    private JButton createLoadButton() {
        JButton button = new JButton();
        button.setText("Load Game..");
        button.addActionListener(GameAction.loadAction(this));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  
        button.setMaximumSize(new Dimension(150,30));
        return button;
    }
    
    @Override
    protected void doUponExit() {
        for (GameWindow game : games) game.dispose();
        System.exit(0);
    }
}
