package gameengine;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.GameMap;

import communicators.MessageParser;

/**
 *
 * @author AMILA
 */


public class MapController implements Observer {

    private GameMap map;

    public MapController() 
    {
	MessageParser.getInstance().addObserver(this);
	map = GameMap.getInstance();
    }

    @Override
    public void update(Observable o, Object arg) 
    {
	if (o instanceof MessageParser) 
        {
	    String string = (String) arg;
	    if (string.startsWith("G:")) 
            {
		updateMap(string);
	    } else if (string.startsWith("L:")) 
            {
		setLifePack(string);
	    } else if (string.startsWith("C:")) 
            {
		setCoinPile(string);
	    } else if (string.startsWith("S:")) 
            {
		initialisePlayers(string);
	    } else if (string.startsWith("I:")) 
            {
		initialiseMap(string);
	    } else 
            {
		Logger.getLogger(MapController.class.getName()).log(
			Level.SEVERE, "Un Identified String: {0}", string);
	    }
	}

    }

    private void setCoinPile(String string) 
    {
	map.setCoinPile(string);
    }

    private void initialiseMap(String string) 
    {
	map.initialiseMap(string);
    }

    private void initialisePlayers(String string) 
    {
	map.initialisePlayers(string);
    }

    private void setLifePack(String string) 
    {
	map.setLifePack(string);
    }

    private void updateMap(String string) 
    {
	map.updateMap(string);
    }

}
