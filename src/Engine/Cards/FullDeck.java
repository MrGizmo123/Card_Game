package Engine.Cards;

public class FullDeck extends Deck {

    public FullDeck()
    {
        for(CardType t : CardType.values())
        {
            Card c = new Card(t);
            super.addCard(c);
        }
    }

}
