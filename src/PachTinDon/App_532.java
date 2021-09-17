package PachTinDon;

import Engine.ClientSide.GUI.Input;
import Engine.ClientSide.GUI.RootCotainer;
import Engine.ClientSide.GUI.UIHandler;
import Engine.ClientSide.GUI.Window;

public class App_532 {

    public static Window window;
    public static UIHandler_532 uiHandler;
    public static Game_532 game;
    public static NetworkHandler_532 networkHandler;

    public static void init()
    {
        RootCotainer root = new RootCotainer();

        window = new Window_532(700, 500, root);

        uiHandler = new UIHandler_532(window, root);
        game = new Game_532();
        networkHandler = new NetworkHandler_532(game);
    }

    public static void update()
    {
        networkHandler.update();
        uiHandler.update();

        Input.update();
    }

}
