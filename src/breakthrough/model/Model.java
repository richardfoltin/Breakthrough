package breakthrough.model;

import breakthrough.gui.GameWindow;
import breakthrough.gui.MenuWindow;
import java.util.ArrayList;

public class Model {

    private int size;

    private Player actualPlayer;
    private Field selectedField;
    private GameWindow game;
    private ArrayList<Field> fields;

    /**
     * A játék modeljének konstruktora
     * 
     * @param size a játék mérete
     * @param fields a mezők listája
     * @param startingPlayer a kezdő játékos
     */
    public Model(int size, ArrayList<Field> fields, Player startingPlayer) {
        this.size = size;
        this.fields = fields;
        this.actualPlayer = startingPlayer;
        this.selectedField = null;
    }

    /**
     * A játékos kiválaszt egy mezőt.
     * Leellenőrzi, hogy a mező kiválasztható-e, és ha igen akkor kiválasztja
     * 
     * @param field a kiválasztott mező
     */
    public void select(Field field) {
        if (field.getPlayer() == actualPlayer) {
            selectedField = field;
            System.out.println("Select: " + field);
            selectedField.selecedUnitBackground();
            game.updateFooterText();
            
            for (Field f : fields) {
                if (actualPlayer.canMove(selectedField, f)) {
                    if (f.getPlayer() != null) f.attackBackground();
                    else f.selectBackground();
                }
            }
        } else {
            game.cantSelectThis();
        }
    }
  
    /**
     * A játékos rálép egy meőre
     * Leellenőrzi, hogy a játékos rá tud-e lépni a mezőre, és ha igen, akkor rálép
     * 
     * @param field a célmező
     */
    public void step(Field field) {
        fields.forEach((f) -> {
            f.clearBackground();
        });
        
        if (!actualPlayer.canMove(selectedField, field)) {
            if (field.getPlayer() == actualPlayer) {
                select(field);
            } else {
                selectedField = null;
                game.cantMoveHere();
            }
        } else {
            field.setPlayer(actualPlayer);
            selectedField.removePlayer();

            System.out.println("Move: " + field);
            selectedField = null;
            Player winner = findWinner();
            if (winner != null) game.showGameOverMessage(winner);
            
            actualPlayer = (actualPlayer == Player.HUMAN) ? Player.ORC : Player.HUMAN;
            game.updateFooterText();
        }

    }

    /**
     * Leellenőrzi, hogy van-e nyertese a játéknak
     * 
     * @return a nyertes játékos, ha van. Egyébként null
     */
    public Player findWinner() {
        for (Field field : fields) {
            if (field.getColumn() == 0 && field.getPlayer() == Player.ORC) return Player.ORC;
            if (field.getColumn() == size-1 && field.getPlayer() == Player.HUMAN) return Player.HUMAN;
        }

        return null;
    }
    
    /**
     * Elindítja a modelhez tartozó játékablakot
     */
    public void startGameWindow() {
        this.game = new GameWindow(this);
        MenuWindow.getInstance().addGame(game);
        game.setVisible(true);
    }
    
    /**
     * Újraindítja a modelhez tartozó játékot.
     */
    public void resetGame() {
        for (Field field : fields) field.resetToDefault();
        actualPlayer = Player.HUMAN;
        selectedField = null;
        game.updateFooterText();
    }
    
    public int getSize() {return size;}
    public Player getActualPlayer() {return actualPlayer;}   
    public boolean selectionComes() {return (selectedField == null);}
    public ArrayList<Field> getFields() { return fields;}
    

}
