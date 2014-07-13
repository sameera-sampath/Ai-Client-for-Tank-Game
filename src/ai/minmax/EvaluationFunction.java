package ai.minmax;

import java.util.ArrayList;
import models.Coin;
import models.MapObject;
import models.Player;

/**
 *
 * @author AMILA
 */

public class EvaluationFunction {
    
    public static int getEvaluationValue(int x, int y)
    {
        int val = 0;
        MapObject[][] map = gameengine.GameEngine.getInstance().getMap();
        Coin[] coins = getCoinPiles(map);
        Player thisPlayer = gameengine.GameEngine.getInstance().getThisPlayer();
        for(int i=0;i<coins.length;i++)
        {         
            int dis = Math.abs(coins[i].getX()-thisPlayer.getX()) + Math.abs(coins[i].getY()-thisPlayer.getY());
            val += (int)10000/dis * coins[i].getValue() * (dis < coins[i].getTimeToLive() ? 0.5 : 1);
        }
        
        if(gameengine.GameEngine.getInstance().getThisPlayer().getHealth()<50)
        {
            // has to implement
        }
        
        return val;
    }
    
    
    private static Coin[] getCoinPiles(MapObject[][] map)
    {
        ArrayList<MapObject> coins = new ArrayList<MapObject>();
        for(int i=0;i<gameengine.GameEngine.SIZE;i++)
        {
            for(int j=0;j<gameengine.GameEngine.SIZE;j++)
            {
                if(map[i][j] instanceof Coin)
                {
                    coins.add(map[i][j]);
                }
            }
        }
       return coins.toArray(new Coin[coins.size()]);
    }
}
