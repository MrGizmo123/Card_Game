package Engine.Cards;

public class Card {

    protected CardType type;

    public Card(CardType t)
    {
        this.type = t;
    }

    public CardType getType()
    {
        return type;
    }
}
