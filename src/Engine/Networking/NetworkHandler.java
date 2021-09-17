package Engine.Networking;

import Engine.ClientSide.GUI.UIHandler;
import Engine.Game;
import PachTinDon.App_532;
import PachTinDon.Game_532;

public abstract class NetworkHandler {

    private static final int PORT = 25565;

    private static Client client;

    public NetworkHandler()
    {
        String hostIP = App_532.uiHandler.getIP();
        String username = App_532.uiHandler.getUsername();

        client = new Client(hostIP, PORT, username);

        new Thread(client).start();
        client.sendMessage("hi");
    }

    public void update()
    {
        Message nextMsg = null;
        while((nextMsg = client.getNextMessage()) != null) {

            String message = nextMsg.getMsg();

            System.out.println("Message received " + message);

            if (message.contains("=")) {
                String[] functionAndParams = message.split("=");

                String function = functionAndParams[0];
                String argument = functionAndParams[1];

                handleCommand(function, argument);

            }
        }
    }

    public abstract void handleCommand(String function, String argument);

    public static String getUsername()
    {
        return client.getUsername();
    }

    public static void sendMessage(String message)
    {
        client.sendMessage(message);
    }

}
