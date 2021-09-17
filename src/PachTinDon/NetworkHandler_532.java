package PachTinDon;

import Engine.ClientSide.GUI.UIHandler;
import Engine.Game;
import Engine.Networking.Client;
import Engine.Networking.Message;
import Engine.Networking.NetworkHandler;

public class NetworkHandler_532 extends NetworkHandler {

    private Game_532 game;

    public NetworkHandler_532(Game_532 game)
    {
        this.game = game;
    }

    @Override
    public void handleCommand(String function, String argument)
    {
        if(function.equals("playerEntered"))
        {
            game.playerEntered(argument);
        }
        else if(function.equals("gameStarted"))
        {
            game.gameStarted();
        }
        else if(function.equals("requiredHaat"))
        {
            game.requiredHaatReceived(argument);
        }
        else if(function.equals("addCards"))
        {
            game.addCards(argument);
        }
        else if(function.equals("alert"))
        {
            game.alert(argument);
        }
        else if(function.equals("hukum"))
        {
            game.hukumSelected(argument);
        }
        else if(function.equals("whoseTurn"))
        {
            game.whoseTurn(argument);
        }
        else if(function.equals("placedCards"))
        {
            game.updatePlacedCards(argument);
        }
        else if(function.equals("haatMade"))
        {
            game.haatMade();
        }
    }

}
