package breakthrough.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

public class Field extends JButton {
    
    private final int row;
    private final int column;
    private Player player;
    private final Model model;
    
    private static final Color NORMALBGCOLOR = new JButton().getBackground();
    private static final Color SELECTBGCOLOR = new Color(210,210,210);
    private static final Color SELECTEDUNITBGCOLOR = new Color(173,173,173);
    private static final Color ATTACKBGCOLOR = new Color(255,140,140);

    /**
     * A mező konstruktora, mely meghívja a privát konstruktort alapértelmezett játékost beállítva.
     * 
     * @param model a játékhoz tartozó model
     * @param row a mező sorána indexe
     * @param column a mező oszlopának indexe
     */
    public Field(Model model, final int row, final int column) {
        this(model,row,column,true);
    }
    
    /**
     * A mező konstruktora, mely meghívja a privát konstruktort és beállítja a megadott játékost
     * 
     * @param model a játékhoz tartozó model
     * @param row a mező sorána indexe
     * @param column a mező oszlopának indexe
     * @param player a mezőhöz hozzárendelt játékos
     */
    public Field(Model model, final int row, final int column, Player player) {
        this(model,row,column,false);
        setPlayer(player);
    }
    
     /**
     * A mező privát konstruktora.
     * Beállítja az induló álloptot.
     * Minden mezőhöz eseménykezelőt rendel hozzá, ami elvégzi a kijelölést és a lépést.
     * 
     * @param model a játékhoz tartozó model
     * @param row a mező sorána indexe
     * @param column a mező oszlopának indexe
     * @param defaultPlayer true, ha a mezőre a játék indításakori alapértelmezett játákost kell rakni
     */   
    private Field(Model model, final int row, final int column, boolean defaultPlayer){
        this.row = row;
        this.column = column;
        this.model = model;
        this.clearBackground();   
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.selectionComes()) model.select(Field.this);
                else model.step(Field.this);
            }
        });
        
        if (defaultPlayer) setDefaultPlayer();
    }
    
    /**
     * Beállítja a mezőre a új játék indításakori alapértelmezett játékost.
     */
    public void setDefaultPlayer(){
        if (column == 0) setPlayer(Player.HUMAN);
        else if (column == model.getSize()-1) setPlayer(Player.ORC);
        else removePlayer();
    }
    
    /**
     * A mezőhöz hozzárendel egy játékost
     * 
     * @param player a hozzárendelendő játékos
     */
    public void setPlayer(Player player){
        this.player = player;
        
        if (player != null) this.setIcon(player.getIcon());
        else removePlayer();
    }
    
    /**
     * A mezőről eltávolítja a játékost
     */
    public void removePlayer(){
        this.player = null;
        this.setIcon(null);
    }
    
    /**
     * A mezőt a játék indításakori alapértelmezett értékre állítja
     */
    public void resetToDefault() {
        setDefaultPlayer();
        clearBackground();
    }

    public void clearBackground() {this.setBackground(NORMALBGCOLOR);}
    public void selectBackground() {this.setBackground(SELECTBGCOLOR);}
    public void attackBackground() {this.setBackground(ATTACKBGCOLOR);}
    public void selecedUnitBackground() {this.setBackground(SELECTEDUNITBGCOLOR);}

    public Player getPlayer() {return player;}
    public int getRow() {return row;}
    public int getColumn() {return column;}

    @Override
    public String toString() {
        return "Field{" + "row=" + row + ", column=" + column + ", player=" + player + '}';
    }
}
