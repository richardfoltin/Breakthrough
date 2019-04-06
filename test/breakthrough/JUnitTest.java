package breakthrough;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import breakthrough.model.Model;
import breakthrough.model.Field;
import breakthrough.model.Player;

public class JUnitTest {
    
    private Model model;
    private ArrayList<Field> fields;
    private int size;
    
    public static void main(String[] args) {
    Result result = JUnitCore.runClasses(JUnitTest.class);
    for (Failure failure : result.getFailures()) {
        System.out.println(failure.toString());
    }
  }
    
    @Before
    public void setUp() {
        size = 6;
        fields = new ArrayList<>();
        model = new Model(size, fields, Player.HUMAN);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Field field = new Field(model, i, j);
                fields.add(field);
            }
        }
        model.startGameWindow(false);
    }
    
    @Test
    public void startTest() {
        
        assertEquals("Human player should start",Player.HUMAN,model.getActualPlayer());
        
        for (Field f : fields) {
            if (f.getColumn() == 0) assertEquals("Human should be at column 0",Player.HUMAN,f.getPlayer());
            if (f.getColumn() == 1) assertEquals("Human should be at column 1",Player.HUMAN,f.getPlayer());
            if (f.getColumn() == 2) assertNull("Nobody should be at column 2",f.getPlayer());
            if (f.getColumn() == 3) assertNull("Nobody should be at column 3",f.getPlayer());
            if (f.getColumn() == 4) assertEquals("Orc should be at column 4",Player.ORC,f.getPlayer());
            if (f.getColumn() == 5) assertEquals("Orc should be at column 5",Player.ORC,f.getPlayer());   
        } 
    }
    
    @Test
    public void moveTest_1() {
        Field fromField = getField(1,1);
        Field toField = getField(0,2);
        model.select(fromField);
        model.step(toField);
        assertEquals("Human should be able to move there!",Player.HUMAN,toField.getPlayer());
        assertEquals("Orc player should come next",Player.ORC,model.getActualPlayer());
    }
     
    @Test
    public void moveTest_2() {
        Field fromField = getField(1,1);
        Field toField = getField(1,2);
        model.select(fromField);
        model.step(toField);
        assertEquals("Human should be able to move there!",Player.HUMAN,toField.getPlayer());
        assertEquals("Orc player should come next",Player.ORC,model.getActualPlayer());
    }
    
    @Test
    public void moveTest_3() {
        Field fromField = getField(1,1);
        Field toField = getField(2,2);
        model.select(fromField);
        model.step(toField);
        assertEquals("Human should be able to move there!",Player.HUMAN,toField.getPlayer());
        assertEquals("Orc player should come next",Player.ORC,model.getActualPlayer());
    }
    
    @Test
    public void moveTest_4() {
        Field fromField = getField(1,1);
        Field toField = getField(3,2);
        model.select(fromField);
        model.step(toField);
        assertNotEquals("Human shouldn't be able to move there!",Player.HUMAN,toField.getPlayer());
        assertEquals("Human player should come next",Player.HUMAN,model.getActualPlayer());
    }
    
    @Test
    public void moveTest_5() {
        Field fromField = getField(1,1);
        Field toField = getField(1,3);
        model.select(fromField);
        model.step(toField);
        assertNotEquals("Human shouldn't be able to move there!",Player.HUMAN,toField.getPlayer());
        assertEquals("Human player should come next",Player.HUMAN,model.getActualPlayer());
    }
    
    private Field getField(int row, int column) {
        for (Field f : fields) {
            if (f.getRow() == row && f.getColumn() == column) return f;
        }
        return null;
    }
    
    @After
    public void tearDown() {
        model.getGame().dispose();
    }
        
}
