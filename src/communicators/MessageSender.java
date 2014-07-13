package communicators;

/**
 *
 * @author AMILA
 */

public class MessageSender {
    
    private SenderSocket socket;

    public MessageSender() 
    {
        socket = new SenderSocket();
    }
    
    public void sendMessage(String message)
    {
    	socket.sendMessage(message);
    }
}
