package Engine.ClientSide.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input {

    private static boolean mouseClicked;
    private static Point mousePos;

    public static void init(Window w)
    {
        mousePos = new Point();
        mouseClicked = false;

        w.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    mouseClicked = true;

                    //System.out.println("mouse clicked");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        w.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                mousePos.x = e.getX();
                mousePos.y = e.getY();

            }
        });
    }

    public static void update()
    {
        mouseClicked = false;
    }


    public static boolean isMouseClicked() {
        return mouseClicked;
    }

    public static float getMouseX()
    {
        return mousePos.x;
    }

    public static float getMouseY()
    {
        return mousePos.y;
    }
}
