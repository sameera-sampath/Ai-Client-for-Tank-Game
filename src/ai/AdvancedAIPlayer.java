package ai;

import ai.minmax.MaxNode;
import gameengine.GameEngine;
import java.util.Observable;
import java.util.Observer;
import models.Player;

/**
 *
 * @author AMILA
 */



public class AdvancedAIPlayer implements Observer {
    public static final int INF = 999999999;

    public void update(Observable o, Object arg) {
        
        if (o instanceof GameEngine) {
            if (((String) arg).equals("GLOBAL_UPDATE")) {
                Player[] players = gameengine.GameEngine.getInstance().getPlayers();
                Integer[] minPos = new Integer[2];
                Integer[] maxPos = new Integer[2];
                minPos[0] = minPos[1] = 0;
                for(int i=0;i<players.length;i++){
                    if(players[i].isOpponent()){
                        minPos[0] += players[i].getX()/4;
                        minPos[1] += players[i].getY()/4;
                    }else{
                        maxPos[0] = players[i].getX();
                        maxPos[1] = players[i].getY();
                    }
                }
                
                MaxNode maxRoot = new MaxNode(maxPos, minPos, 10);
                System.out.println(maxRoot.getVal(-INF, INF));
                
            }
        }
    }
}