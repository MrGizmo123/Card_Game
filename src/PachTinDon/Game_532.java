package PachTinDon;

import Engine.Cards.Card;
import Engine.Cards.Deck;
import Engine.Cards.Suite;
import Engine.ClientSide.GUI.UIHandler;
import Engine.ClientSide.GameLog;
import Engine.Game;

public class Game_532 extends Game {

    private Deck privateDeck;
    private Deck placedDeck;

    private int haatMade;
    private int haatRequired;

    public Game_532()
    {
        super();

        privateDeck = App_532.uiHandler.getPrivateDeck();
        placedDeck = App_532.uiHandler.getPlacedDeck();

        gameLog = new GameLog();

        haatMade = 0;
        haatRequired = 0;
    }

    public void playerEntered(String playername)
    {
        String alert = playername + " joined the game";

        App_532.uiHandler.alert(alert);
        gameLog.append(alert);
    }

    public void gameStarted()
    {
        String alert = "The Game has started";

        App_532.uiHandler.alert(alert);
        gameLog.append(alert);
    }

    public void requiredHaatReceived(String reqHaat)
    {
        haatRequired = Integer.parseInt(reqHaat);

        App_532.uiHandler.setRequiredHaat(haatRequired);
        gameLog.append("Required haat received: " + haatRequired);

        if(haatRequired == 5)
        {
            Engine.Cards.Suite hukum = App_532.uiHandler.askForHukum();

            NetworkHandler_532.sendMessage("hukum=" + hukum.toString());
            App_532.uiHandler.alert("Hukum selected");
            App_532.uiHandler.setHukum(hukum);

            gameLog.append("Hukum selected: " + hukum.toString());
        }
    }

    public void addCards(String cardList)
    {
        App_532.uiHandler.addCards(new Engine.Cards.Deck(cardList));
        gameLog.append("Added cards: " + cardList + ", Current private Deck: " + privateDeck.toString());
    }

    public void alert(String message)
    {
        App_532.uiHandler.alert(message);
        gameLog.append("Alerted: " + message);
    }
    public void hukumSelected(String hukumString)
    {
        Suite hukum = Suite.valueOf(hukumString);

        App_532.uiHandler.setHukum(hukum);
        gameLog.append("Hukum: " + hukumString);
    }

    public void whoseTurn(String whoseTurn)
    {
        if(whoseTurn.equals(NetworkHandler_532.getUsername()))
        {
            App_532.uiHandler.yourTurn();
        }

        gameLog.append(whoseTurn + "'s turn");
    }

    public void updatePlacedCards(String cardList)
    {
        App_532.uiHandler.updatePlacedDeck(new Engine.Cards.Deck(cardList));

        gameLog.append("Placed cards: " + cardList);
    }

    public void haatMade()
    {
        haatMade++;
        App_532.uiHandler.setHaat(haatMade);
        App_532.uiHandler.alert("You made a haat");

        gameLog.append("Haat Made, current haat" + haatMade);
    }

    public void placeCard(Card c) //checks legality of placing card
    {
        Suite main = null;
        try {
            main = placedDeck.getCards().get(0).getType().getSuite();

            if(checkIfWeHaveThisSuite(main))
            {
                if(c.getType().getSuite() != main)
                {
                    App_532.uiHandler.alert("You have the main suite, you cannot place another suite");
                    return;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            //continues on if placed deck is empty
        }

        privateDeck.remove(c);

        App_532.uiHandler.turnEnded();

        String cardName = c.getType().name();
        NetworkHandler_532.sendMessage("cardPlaced=" + cardName); //tell the server
    }

    private boolean checkIfWeHaveThisSuite(Suite s)
    {
        System.out.println("Current private Deck: " + privateDeck.getCards());
        for(Engine.Cards.Card c : privateDeck.getCards())
        {
            if(c.getType().getSuite() == s)
            {
                return true;
            }
        }

        return false;
    }

}
