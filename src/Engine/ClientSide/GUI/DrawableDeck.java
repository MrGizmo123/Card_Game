package Engine.ClientSide.GUI;

import Engine.Cards.Card;
import Engine.Cards.Deck;
import Engine.Cards.Suite;
import Engine.ClientSide.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;


public class DrawableDeck extends Deck implements GameObject, Container {

    private int width;
    private Point pos;

    private List<DrawableCard> drawableCards;
    private Queue<DrawableCard> selectedCards;

    private int maxSelectionSize;

    public DrawableDeck(int width, int x, int y)
    {
        this.width = width;
        this.pos = new Point(x, y);

        this.drawableCards = new ArrayList<>();
        this.selectedCards = new ConcurrentLinkedQueue<>();

        this.maxSelectionSize = 1;
    }

    public DrawableDeck(Deck d, int width, int x, int y)
    {

        this.width = width;
        this.pos = new Point(x, y);

        this.drawableCards = new ArrayList<>();
        this.selectedCards = new ConcurrentLinkedQueue<>();

        this.maxSelectionSize = 1;

        this.addDeck(d);
    }

    public void cardSelected(DrawableCard c) //callback for children so that selected card count does not exceed max selection count
    {
        selectedCards.add(c);

        if(selectedCards.size() > maxSelectionSize)
        {
            selectedCards.remove().deselect(); // deselects first selected card
        }
    }

    public void cardDeselected(DrawableCard c)
    {
        selectedCards.remove(c);
    }

    @Override
    public void addDeck(Deck d) {
        super.addDeck(d);

        for(Card c : d.getCards())
        {
            DrawableCard drawableCard = new DrawableCard(c, this);
            drawableCards.add(drawableCard);
        }

        recalculateCardPositions();
    }

    @Override
    public void addCard(Card c) {
        super.addCard(c);

        DrawableCard drawableCard = new DrawableCard(c, this);
        drawableCards.add(drawableCard);

        recalculateCardPositions();
    }

    @Override
    public Deck giveRandom(int count) {
        Deck givenCards = super.giveRandom(count);

        for(Card c : givenCards.getCards())
        {
            drawableCards.remove(c);
            selectedCards.remove(c);
        }

        return givenCards;
    }

    @Override
    public void remove(Card c) {
        super.remove(c);

        drawableCards.remove(c);
        selectedCards.remove(c);
    }

    @Override
    public Card giveRandomCard() {

        Card c = super.giveRandomCard();

        drawableCards.remove(c);
        selectedCards.remove(c);

        return c;
    }

    @Override
    public void removeCardsByNumber(Predicate<Integer> conditions) {
        super.removeCardsByNumber(conditions);

        drawableCards.removeIf(c -> conditions.test(c.getType().getNumber()));
        selectedCards.removeIf(c -> conditions.test(c.getType().getNumber()));
    }

    @Override
    public void removeCardsBySuite(Predicate<Suite> conditions) {
        super.removeCardsBySuite(conditions);

        drawableCards.removeIf(c -> conditions.test(c.getType().getSuite()));
        selectedCards.removeIf(c -> conditions.test(c.getType().getSuite()));
    }

    @Override
    public void removeCards(Predicate<Suite> suiteConditions, Predicate<Integer> numberConditions) {
        super.removeCards(suiteConditions, numberConditions);

        drawableCards.removeIf(c -> (suiteConditions.test(c.getType().getSuite()) &&
                                     numberConditions.test(c.getType().getNumber())));

        selectedCards.removeIf(c -> (suiteConditions.test(c.getType().getSuite()) &&
                                     numberConditions.test(c.getType().getNumber())));
    }

    @Override
    public void clear() {
        super.clear();

        drawableCards.clear();
        selectedCards.clear();
    }

    private void recalculateCardPositions()
    {
        int noOfCards = drawableCards.size();

        if(noOfCards == 0)
        {
            return;
        }

        int distanceBetweenCards = this.width / noOfCards;
        int cursor = (int)pos.x;

        for(DrawableCard c : drawableCards)
        {
            c.setPos(cursor, (int)pos.y);

            cursor += distanceBetweenCards;
        }
    }

    @Override
    public void update() {
        for(DrawableCard c: this.getChildren())
        {
            c.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for(DrawableCard c: getChildren())
        {
            c.draw(g);
        }

        //System.out.println("drawn deck");
    }

    @Override
    public List<DrawableCard> getChildren() {
        return drawableCards;
    }

    public Queue<DrawableCard> getSelectedCards()
    {
        return selectedCards;
    }

    public void removeDrawableCard(DrawableCard c)
    {
        drawableCards.remove(c);
        selectedCards.remove(c);
    }

    @Override
    public AABB getBoundingBox() {
        return null;
    }

    @Override
    public void clicked() {

    }

    @Override
    public boolean checkClick(float mouseX, float mouseY)
    {
        //loop backwards to check cards that are on top first
        for(int i=drawableCards.size()-1;i>=0;i--)
        {
            DrawableCard c = drawableCards.get(i);
            if(c.checkClick(mouseX, mouseY)) {
                return true; // if click is detected, consume click event
            }
        }
        return false;
    }
}
