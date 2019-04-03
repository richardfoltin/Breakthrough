package breakthrough.gui;

import breakthrough.Breakthrough;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class BaseWindow extends JFrame {

    /**
     * Beállítja a minden ablakra egységesen vonatkozó tulajdonságokat:
     * - cím
     * - bezárás eseménye
     * - ikon
     */
    public BaseWindow() {
        setTitle("Breakthrough");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }

        });
        URL url = Breakthrough.class.getResource("resources/sword.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

    }
    
    /**
     * A képernyő közepére helyezi a ablakot.
     */   
    protected void centerWindow() {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;  
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()-200) / 2;  
  
        this.setLocation(x, y);  
    }
    
    /**
     * Feldob egy kilépést jóváhagyató ablakot.
     */
    protected void showExitConfirmation() {
        int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (n == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }
    
    /**
     * Ablak bezárása.
     */
    protected void doUponExit() {
        this.dispose();
    }
    
}
