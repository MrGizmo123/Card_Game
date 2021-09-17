package Engine.ClientSide.GUI;

import Engine.ClientSide.GameObject;
import Engine.Cards.Card;
import Engine.Cards.CardType;
import PachTinDon.App_532;

import java.awt.*;
import java.util.Collection;

public class DrawableCard extends Card implements GameObject {

    private static final int SELECTED_Y_OFFSET = -30;

    private Point pos;
    private AABB boundingBox;

    private DrawableDeck parent;

    private boolean isSelected;

    public DrawableCard(CardType c, int x, int y)
    {
        super(c);
        this.pos = new Point(x,y);

        this.boundingBox = new AABB(x, y, CardType.CARD_WIDTH, CardType.CARD_HEIGHT);
    }

    public DrawableCard(Card c, int x, int y)
    {
        super(c.getType());
        this.pos = new Point(x,y);

        this.boundingBox = new AABB(x, y, CardType.CARD_WIDTH, CardType.CARD_HEIGHT);
    }

    public DrawableCard(Card c, DrawableDeck parent)
    {
        super(c.getType());
        this.pos = new Point();
        this.parent = parent;

        this.boundingBox = new AABB(0, 0, CardType.CARD_WIDTH, CardType.CARD_HEIGHT);
    }

    @Override
    public void draw(Graphics g) {

        //System.out.println("drawn card " + type.toString());

        g.drawImage(super.getType().getTexture(), (int)pos.x, (int)pos.y, null);


    }

    @Override
    public void update() {

    }

    public Point getPos()
    {
        return pos;
    }

    public void setPos(int x, int y) {
        this.pos = new Point(x, y);
        this.boundingBox.setPos(this.pos);
    }

    @Override
    public AABB getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void clicked()
    {
        System.out.println("Clicked " + type.name());

        toggleSelection();

        if(isSelected)
        {
            parent.cardSelected(this);
        }
        else
        {
            parent.cardDeselected(this);
        }

        App_532.uiHandler.windowUpdateRequired();
    }

    @Override
    public boolean checkClick(float mouseX, float mouseY) {

        if(this.boundingBox.intersect(new Point((int)mouseX, (int)mouseY)))
        {
            clicked();

            return true;
        }

        return false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void toggleSelection()
    {
        isSelected = !isSelected;

        int offset = (isSelected) ? SELECTED_Y_OFFSET : -SELECTED_Y_OFFSET;

        setPos((int)pos.x, (int)pos.y + offset);

        System.out.println("toggled " + type.name() + ", status: " + isSelected);
    }

    public void deselect()
    {
        isSelected = false;

        int offset = -SELECTED_Y_OFFSET;

        setPos((int)pos.x, (int)pos.y + offset);

        System.out.println("deselcted " + type.name() + ", status: " + isSelected);
    }

    @Override
    public Collection<? extends GameObject> getChildren() {
        return null;
    }

}
