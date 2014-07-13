package models;

/**
 *
 * @author AMILA
 */

public class Shot extends MapObject {
	private int speed, direction;
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

	public Shot(int x, int y, String string,int dir) 
        {
		super(x, y, string);
		this.direction = dir;
	}
	
	public int getDirection() 
        {
		return direction;
	}
	public void setDirection(int direction) 
        {
		this.direction = direction;
	}
	public int getSpeed() 
        {
		return speed;
	}
	public void setSpeed(int speed) 
        {
		this.speed = speed;
	}

}
