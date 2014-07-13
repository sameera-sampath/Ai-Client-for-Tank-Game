package communicators;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AMILA
 */

public class RecieverSocket extends Observable implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    private byte[] buffer;

    public RecieverSocket() 
    {
        int port = Integer.parseInt(Settings.recieverPort);
        try 
        {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) 
        {
            Logger.getLogger(RecieverSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        buffer = new byte[10000];
    }

    @Override
    public void run() 
    {
        listen();
    }

    private void listen() 
    {
        while (true) 
        {
            try 
            {
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                inputStream.read(buffer);
                String message = (new String(buffer)).split("#")[0];

                setChanged();
                notifyObservers(message);
            } catch (IOException ex) 
            {
                Logger.getLogger(RecieverSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
