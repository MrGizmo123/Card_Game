package Engine.ClientSide.GUI;

import Engine.Cards.Deck;
import Engine.Cards.Suite;
import Engine.ClientSide.GameObject;
import PachTinDon.Game_532;
import utils.TimeMeasurer;

public class UIHandler {

    protected Window window;

    protected RootCotainer root;

    public UIHandler(Window w, RootCotainer root)
    {
        this.root = root;

        window = w;
        Input.init(w);
    }

    public void update()
    {
        root.update();

        if(Input.isMouseClicked()) {
            root.checkClick(Input.getMouseX(), Input.getMouseY());
        }

       window.update(); // this will redraw the window only if needed
    }

    public void addGameObject(Engine.ClientSide.GameObject obj)
    {
        root.addObject(obj);
    }

    public GameObject getRoot()
    {
        return root;
    }

    public void removeGameObject(GameObject obj)
    {
        root.removeObject(obj);
    }

    public void alert(String message)
    {
        window.setBanner(message);

        windowUpdateRequired();
    }

    public void windowUpdateRequired()
    {
        window.windowUpdateRequired();
    }

    public void windowUpdateRequiredImmediately()
    {
        window.windowUpdateRequiredImmediately();
    }

    public String getIP()
    {
        return window.askForServerIP();
    }

    public String getUsername()
    {
        return window.askForUsername();
    }

    public Suite askForHukum()
    {
        return window.askForHukum();
    }

}
