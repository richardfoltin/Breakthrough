package breakthrough;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JUnitTest {
    
    int petak;
    
    public static void main(String[] args) {
    Result result = JUnitCore.runClasses(JUnitTest.class);
    for (Failure failure : result.getFailures()) {
        System.out.println(failure.toString());
    }
  }
    
    
    @Before
    public void setUp() {
    }
    
    
    @Test
    public void gameTest() {
        assertEquals("Player 3 should have 8500P",8500,1000);
    }
        
}
