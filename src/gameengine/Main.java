package gameengine;

import ai.AIPlayer;
import ui.Settings;

/**
 *
 * @author AMILA
 */

public class Main {

    public static void main(String[] args) 
    {
        final GameEngine gameEngine = GameEngine.getInstance();
        AIPlayer aIPlayer = new AIPlayer();
        gameEngine.addObserver(aIPlayer);
        
        new Runnable() 
        {
            public void run() 
            {
                Settings dialog = new Settings(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() 
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) 
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        }.run();
    }
}
