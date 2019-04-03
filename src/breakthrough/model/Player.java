package breakthrough.model;

import breakthrough.Breakthrough;
import breakthrough.gui.BaseWindow;
import java.awt.Toolkit;
import static java.lang.Math.abs;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Player {
        HUMAN("H",1,"Human","human_small"),
        ORC("O",-1,"Orc","orc_small")
    ;
    
    private final int direction;
    private final ImageIcon icon;
    private final String name;
    private final String code;

    /**
     * Játékos konstruktora
     * 
     * @param direction a támadás iránya
     * @param name a játékos fajának neve
     * @param iconFileName a játékoshoz tartozó png ikonok file-jának neve
     */
    private Player(final String code, final int direction, final String name, final String iconFileName) {
        this.direction = direction;
        this.name = name;
        this.code = code;
        
        URL url = Breakthrough.class.getResource(String.format("resources/%s.png",iconFileName));
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
    }
    
    /**
     * Megállapítja, hogy a játékos egy bizonyos mezőről egy másik bizonyos mezőre tud-e lépni.
     * 
     * @param fromField indulómező
     * @param toField célmező
     * @return true ha a játékos a megadott mezőre tud lépni
     */
    public boolean canMove(Field fromField, Field toField){
        if (toField.getPlayer() == this) return false;
        if (toField.getColumn() != fromField.getColumn() + direction) return false;
        if (abs(toField.getRow() - fromField.getRow()) > 1) return false;
        if (toField.getPlayer() != null && toField.getRow() == fromField.getRow()) return false;
        
        return true;
    }
    
    /**
     * Visszaadja a játék mentett file-jában lévő játékoskódhoz tartozó játékost
     * 
     * @param code a játékos kódja
     * @return a játékos, ha megfelelő a kód. Egyébként null
     */
    public static Player fromCode(String code){
        for (Player p : Player.values()) {
            if (p.getCode().equals(code)) return p;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return code;
    }
   
    public String getName() {return name;}
    public ImageIcon getIcon() {return icon;}
    public String getCode() {return code;}
    
}
