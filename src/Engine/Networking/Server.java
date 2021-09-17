package Engine.Networking;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.ServerSide.ServerSideGame;
import Engine.Cards.Suite;

// Networking.Server class
public class Server implements Runnable{

    private boolean isServerLocked;

    private ServerSocket server;

    private ConcurrentLinkedQueue<Engine.Networking.Message> messagesReceived;
    private ConcurrentLinkedQueue<ClientHandler> clients;

    public Server()
    {
        messagesReceived = new ConcurrentLinkedQueue<>();
        clients = new ConcurrentLinkedQueue<>();

        isServerLocked = false;
    }

    public void printMessages()
    {
        for(Engine.Networking.Message s : messagesReceived)
        {
            System.out.println(s.getMsg());
        }

        messagesReceived.clear();
    }

    public Engine.Networking.Message getNextMessage()
    {
        return messagesReceived.poll();
    }

    public void sendMessage(String message)
    {
        for(ClientHandler client : clients)
        {
            client.sendMessage(message);

            System.out.println("Sent " + message + " to " + client.name);
        }
    }

    public void sendMessageByIndex(String message, int targetIndex)
    {
        ClientHandler client = (ClientHandler) clients.toArray()[targetIndex];

        client.sendMessage(message);
        System.out.println("Sent " + message + ", selectively to " + client.name);
    }

    public void sendTo(String message, String ... targets)
    {
        List<String> targetList = Arrays.asList(targets);

        for(ClientHandler client : clients)
        {
            if(targetList.contains(client.name))
            {
                client.sendMessage(message);
                System.out.println("Sent " + message + ", selectiveley to " + client.name);
            }
        }
    }

    public void sendToEveryoneExcept(String message, String ... targets)
    {
        List<String> targetList = Arrays.asList(targets);

        for(ClientHandler client : clients)
        {
            if(!targetList.contains(client.name))
            {
                client.sendMessage(message);
                System.out.println("Sent " + message + ", selectiveley to " + client.name);
            }
        }
    }

    private void removeClient(ClientHandler c)
    {
        clients.remove(c);
    }

    public void lockServer()
    {
        isServerLocked = true;
    }

    public void unlockServer()
    {
        isServerLocked = false;

        try {
            startLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int getClientIndexByName(String name)
    {
        ClientHandler[] clientArray = getClientsAsArray();

        for(int i=0;i<clientArray.length;i++)
        {
            if(clientArray[i].name.equals(name))
            {
                return i;
            }
        }

        return -1;
    }

    private void startLoop() throws IOException {
        while (!isServerLocked) {

            Socket client = server.accept();

            System.out.println("New client connected " + client.getInetAddress().getHostAddress());

            ClientHandler clientSock = new ClientHandler(client, this);
            new Thread(clientSock).start();

            addClient(clientSock);
        }
    }

    public void startServer()
    {
        try {
            server = new ServerSocket(25565);
            server.setReuseAddress(true);

            System.out.println("Server started");

            startLoop();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleMessage(Engine.Networking.Message msg)
    {
        messagesReceived.add(msg);
    }

    private void addClient(ClientHandler client)
    {
        clients.add(client);

        ServerSideGame.playerEntered(clients.size());
    }

    public String getClientNameByIndex(int index)
    {
        return getClientsAsArray()[index].name;
    }

    private ClientHandler[] getClientsAsArray()
    {
        ClientHandler[] clientArray = new ClientHandler[clients.size()];
        clients.toArray(clientArray);

        return clientArray;
    }

    @Override
    public void run() {
        startServer();
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        private Server parentServer;

        private PrintWriter out;
        private BufferedReader in;

        private String name;

        // Constructor
        public ClientHandler(Socket socket, Server parentServer)
        {
            this.clientSocket = socket;
            this.parentServer = parentServer;

            this.name = socket.getInetAddress().getHostName();
        }

        public void sendMessage(String message)
        {
            out.println(message);
            out.flush();
        }

        private void handleMessage(String line)
        {
            String[] separated = line.split("=");
            if(separated[0].equals("name"))
            {
                name = separated[1];
                parentServer.sendToEveryoneExcept("playerEntered=" + name, this.name);

                sendMessage("alert=Connected successfully to server");

                return;
            }
            else if(separated[0].equals("hukum"))
            {
                parentServer.sendToEveryoneExcept(line, this.name);
                ServerSideGame.hukumChosen(Suite.valueOf(separated[1]));
            }
            else if(separated[0].equals("cardPlaced"))
            {
                ServerSideGame.cardPlaced(parentServer.getClientIndexByName(name), separated[1]);
            }

            System.out.println("Received " + line + " from " + name);

            parentServer.handleMessage(new Message(name, line));

        }

        public String getName()
        {
            return name;
        }

        public void run()
        {

            try {

                // get the outputstream of client
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    handleMessage(line);
                }
            } catch (IOException e) {
                if(e instanceof SocketException)
                {
                    System.out.println("Client " + name + " disconnected");
                    parentServer.removeClient(this);
                }
            }  finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}