package Engine.ClientSide.GUI;

import Engine.ClientSide.GameObject;

import java.awt.*;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RootCotainer implements GameObject {

    private Queue<GameObject> children;

    public RootCotainer()
    {
        children = new ConcurrentLinkedQueue<>();
    }

    public void addObject(GameObject obj)
    {
        children.add(obj);
    }

    public void removeObject(GameObject obj)
    {
        children.remove(obj);
    }

    @Override
    public Collection<? extends GameObject> getChildren() {
        return children;
    }

    @Override
    public void update() {
        for(GameObject obj : children)
        {
            obj.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for(GameObject obj : children)
        {
            obj.draw(g);
        }
    }

    @Override
    public AABB getBoundingBox() {
        return null;
    }

    @Override
    public void clicked() {

    }

    @Override
    public boolean checkClick(float mouseX, float mouseY) {

        for(GameObject o : getChildren())
        {
            if(o.checkClick(mouseX, mouseY))
                return true; // if click is detected, consume click event
        }

        return false;
    }
}
