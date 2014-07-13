package communicators;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author AMILA
 */

public class MessageParser extends Observable implements Observer {

    private static MessageParser messageParser = null;
    private MessageReciever messageReciever;
    private MessageSender messageSender;
    private String north;
    private String south;
    private String east;
    private String west;
    private String shoot;
    private String join;

    public static MessageParser getInstance() 
    {
        if (MessageParser.messageParser == null) 
        {
            MessageParser.messageParser = new MessageParser();
        }
        return MessageParser.messageParser;
    }

    private MessageParser() 
    {
        messageReciever = new MessageReciever();
        messageSender = new MessageSender();
        messageReciever.addObserver(this);
        north = "UP#";
        south = "DOWN#";
        east = "RIGHT#";
        west = "LEFT#";
        shoot = "SHOOT#";
        join = "JOIN#";
    }

    public void up() 
    {
        messageSender.sendMessage(north);
    }

    public void down() 
    {
        messageSender.sendMessage(south);
    }

    public void left() 
    {
        messageSender.sendMessage(west);
    }

    public void right() 
    {
        messageSender.sendMessage(east);
    }

    public void shoot() 
    {
        messageSender.sendMessage(shoot);
    }

    public void join() 
    {
        messageSender.sendMessage(join);
    }

    @Override
    public void update(Observable o, Object arg) 
    {
        if (o instanceof MessageReciever) 
        {
            setChanged();
            notifyObservers((String) arg);
        }

    }
}
