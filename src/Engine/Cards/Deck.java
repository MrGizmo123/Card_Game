package Engine.Cards;

import utils.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Deck {

    private int maxSize;
    private List<Card> cards;

    public Deck()
    {
        this.maxSize = 52;
        cards = new ArrayList<>();
    }

    public Deck(String cardList)
    {
        this.maxSize = 52;
        cards = new ArrayList<>();

        convertToDeck(cardList);
    }

    public Deck(Deck d)
    {
        this.maxSize = d.getMaxSize();
        cards = new ArrayList<>();

        this.addDeck(d);
    }

    public Deck(int maxSize)
    {
        this.maxSize = maxSize;
        cards = new ArrayList<>();
    }

    public Deck giveRandom(int count)
    {
        Deck result = new Deck();
        for(int i=count;i>0;i--)
        {
            Card selected = this.giveRandomCard();
            result.addCard(selected);
        }

        return result;
    }

    public void remove(Card c)
    {
        cards.remove(c);
    }

    public Card giveRandomCard()
    {
        int range = cards.size() - 1;
        int randomIndex = Maths.getRandom(range);

        return cards.remove(randomIndex);
    }

    public void addDeck(Deck d)
    {
        cards.addAll(d.getCards());
    }

    public void addCard(Card c)
    {
        if(cards.size() == maxSize)
            return;

        cards.add(c);
    }

    public List<Card> getCards()
    {
        return cards;
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public void removeCardsByNumber(Predicate<Integer> conditions)
    {
        cards.removeIf(c -> conditions.test(c.getType().getNumber()));
    }

    public void removeCardsBySuite(Predicate<Suite> conditions)
    {
        cards.removeIf(c -> conditions.test(c.getType().getSuite()));
    }

    public void removeCards(Predicate<Suite> suiteConditions, Predicate<Integer> numberConditions)
    {
        cards.removeIf(c -> (suiteConditions.test(c.getType().getSuite()) &&
                             numberConditions.test(c.getType().getNumber())));
    }

    @Override
    public String toString()
    {
        if(cards.size() == 0)
        {
            return "empty";
        }

        StringBuilder result = new StringBuilder();

        for(Card c : cards)
        {
            result.append(c.getType().toString() + ",");
        }

        result.deleteCharAt(result.length()-1); //remove exccess comma at the end

        return result.toString();
    }

    private void convertToDeck(String s)
    {
        if (s.equals("empty"))
        {
            return;
        }

        String[] cardList = s.split(",");

        for(String card : cardList)
        {
            Card c = new Card(CardType.valueOf(card));

            cards.add(c);
        }
    }

    public void clear()
    {
        this.cards.clear();
    }

}
