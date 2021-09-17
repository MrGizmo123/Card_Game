package PachTinDon;

import Engine.Cards.Suite;
import Engine.ClientSide.GUI.RootCotainer;
import Engine.ClientSide.GUI.Window;

import javax.swing.*;

public class Window_532 extends Window {

    private BottomControlPanel controls;

    private JSplitPane splitPane;

    public Window_532(int width, int height, RootCotainer container)
    {
        super(width, height, container);

        controls = new BottomControlPanel();

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setEnabled(false);

        splitPane.setTopComponent(canvas);
        splitPane.setBottomComponent(controls);

        this.add(splitPane);
    }

    public void setRequiredHaat(int requirement)
    {
        controls.setRequiredHaat(requirement);
    }

    public void setHukum(Suite hukum)
    {
        controls.setHukumLabel(hukum);
    }

    public void setCurrentHaat(int currentHaat)
    {
        controls.setCurrentHaat(currentHaat);
    }

    public void enableCardPlacement()
    {
        controls.enablePlaceButton();
    }

    public void disableCardPlacement()
    {
        controls.disablePlaceButton();
    }
}
