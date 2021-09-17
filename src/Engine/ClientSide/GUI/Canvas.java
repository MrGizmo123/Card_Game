package Engine.ClientSide.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Canvas extends JPanel {

    private static final int BANNER_TIME = 5000;
    private static final int BANNER_SIZE = 20;

    private static final Font BANNER_FONT = new Font("Verdana", Font.PLAIN, BANNER_SIZE);

    private String banner;

    private Queue<String> bannerQueue;

    private long bannerStartTime;

    private boolean isRedrawNeeded;

    private RootCotainer rootCotainer;

    public Canvas(RootCotainer rootCotainer)
    {
        this.rootCotainer = rootCotainer;

        bannerQueue = new ConcurrentLinkedQueue<>();
        banner = "";
        isRedrawNeeded = true;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.setFont(BANNER_FONT);
        g.drawString(banner, 0, BANNER_SIZE);

        rootCotainer.draw(g);

        //System.out.println("Redrawn");

        isRedrawNeeded = false; //set it to false after redrawing canvas
    }

    public void canvasUpdateRequired()
    {
        //System.out.println("Canvas update is required");

        this.isRedrawNeeded = true;
    }

    public void canvasUpdateRequiredImmediately()
    {
        super.repaint();
    }

    public void update()
    {
        checkBannerTimeout();

        if(isRedrawNeeded)
            super.repaint();
    }

    public void setBanner(String banner)
    {
        this.banner = banner;
        bannerStartTime = System.currentTimeMillis();

        isRedrawNeeded = true;
    }

    public void addBannerToQueue(String banner)
    {
        bannerQueue.add(banner);
    }

    private void checkBannerTimeout()
    {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - bannerStartTime;

        if(bannerQueue.size() > 0)
        {
            setBanner(bannerQueue.poll());   //checks if new banner is there and if there is then puts it on
        }

        if(elapsedTime > BANNER_TIME)
        {
            setBanner((bannerQueue.peek() == null) ? ("") : (bannerQueue.poll()));  //puts the next banner on, puts "" if next element is null (if queue is empty)
        }
    }
}
