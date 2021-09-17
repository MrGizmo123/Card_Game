package Engine.Networking;
import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client implements Runnable{

    private ConcurrentLinkedQueue<Engine.Networking.Message> messagesReceived;

    private PrintWriter out;
    private BufferedReader in;
    private String host;
    private int hostPort;
    private String name;

    public Client(String host, int hostPort, String name)
    {
        messagesReceived = new ConcurrentLinkedQueue<>();

        this.host = host;
        this.hostPort = hostPort;

        this.name = name;
    }

    public void setUserName(String userName)
    {
        this.name = userName;
        sendMessage("name=" + name);
    }

    public String getUsername()
    {
        return name;
    }

    public void startClient()
    {
        try (Socket socket = new Socket(host, hostPort)) {
            System.out.println("Client started");

            out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            //send username to server
            sendMessage("name=" + name);

            while (true) {
                String msg = in.readLine();

                Engine.Networking.Message message = new Engine.Networking.Message(socket.getInetAddress().getHostAddress(), msg);

                handleMessage(message);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Engine.Networking.Message message)
    {
        messagesReceived.add(message);
    }

    public Message getNextMessage()
    {
        return messagesReceived.poll();
    }

    public void sendMessage(String msg)
    {
        while (true)  // keep trying till out != null
        {
            try
            {
                out.println(msg);
                out.flush();
                break;
            }
            catch (NullPointerException e)
            {
                continue;
            }
        }
    }

    @Override
    public void run() {
        startClient();
    }
}