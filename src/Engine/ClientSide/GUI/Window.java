package Engine.ClientSide.GUI;

import Engine.Cards.Suite;
import utils.Utils;

import javax.swing.*;
import java.awt.event.*;

public class Window extends JFrame {

    protected Canvas canvas;

    public Window(int width, int height, RootCotainer container)
    {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        canvas = new Canvas(container);

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setVisible(true);
    }

    public void update()
    {
        canvas.update();
    }

    @Override
    public void addMouseListener(MouseListener m)
    {
        canvas.addMouseListener(m);
    }

    @Override
    public void addMouseMotionListener(MouseMotionListener m)
    {
        canvas.addMouseMotionListener(m);
    }

    public String askForServerIP()
    {
        String ip = null;
        String port = null;

        do{
            ip = JOptionPane.showInputDialog("Enter The Server IP (Make sure it is correct)");

        }while(ip == null || ip.isEmpty() || !Utils.validIP(ip));

        return ip;
    }

    public String askForUsername()
    {
        String name = null;
        do{
            name = JOptionPane.showInputDialog("Enter your username");

        }while(name == null || name.isEmpty());

        this.setTitle(name);

        return name;
    }

    public Suite askForHukum()
    {
        String choices[] = {"Spades", "Diamonds", "Clubs", "Hearts"};

        int choice;
        do{
            choice = JOptionPane.showOptionDialog(null, "Choose your hukum", this.getTitle() + " Hukum select", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, null);

        }while(choice==-1);

        if(choice == 0)
        {
            return Suite.SPADES;
        }
        else if(choice == 1)
        {
            return Suite.DIAMONDS;
        }
        else if(choice == 2)
        {
            return Suite.CLUBS;
        }
        else if(choice == 3)
        {
            return Suite.HEARTS;
        }

        return null;
    }

    public void setBanner(String banner)
    {
        canvas.addBannerToQueue(banner);
    }

    public void windowUpdateRequired()
    {
        canvas.canvasUpdateRequired();
    }

    public void windowUpdateRequiredImmediately()
    {
        canvas.canvasUpdateRequiredImmediately();
    }
}
