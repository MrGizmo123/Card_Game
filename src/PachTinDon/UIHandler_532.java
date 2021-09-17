package PachTinDon;

import Engine.Cards.Deck;
import Engine.Cards.Suite;
import Engine.ClientSide.GUI.*;
import Engine.ClientSide.GameObject;

public class UIHandler_532 extends UIHandler {

    private DrawableDeck privateDeck;
    private DrawableDeck placedDeck;

    public UIHandler_532(Window w, RootCotainer root)
    {
        super(w, root);

        privateDeck = new DrawableDeck(new Deck(), 400, 100 , 230);
        placedDeck = new DrawableDeck(new Deck(), 100, 250 , 30);

        root.addObject(privateDeck);
        root.addObject(placedDeck);
    }

    public Deck getPrivateDeck() {
        return privateDeck;
    }

    public Deck getPlacedDeck() {
        return placedDeck;
    }

    public void setRequiredHaat(int requiredHaat)
    {
        ((Window_532)window).setRequiredHaat(requiredHaat);
    }

    public void addCards(Deck cards)
    {
        privateDeck.addDeck(cards);

        windowUpdateRequiredImmediately();
    }

    public void updatePlacedDeck(Deck d)
    {
        placedDeck.clear();
        placedDeck.addDeck(d);

        windowUpdateRequired();
    }

    public void setHukum(Suite hukum)
    {
        ((Window_532)window).setHukum(hukum);
    }

    public void yourTurn()
    {
        ((Window_532)window).enableCardPlacement();
    }

    public void turnEnded()
    {
        ((Window_532)window).disableCardPlacement();
    }

    public void setHaat(int val)
    {
        ((Window_532)window).setCurrentHaat(val);
    }

    public void cardPlaced()
    {
        if(privateDeck.getSelectedCards().size() == 0)
        {
            window.setBanner("No card selected");
            return;
        }

        App_532.game.placeCard(privateDeck.getSelectedCards().peek());

        windowUpdateRequired();
    }

}
