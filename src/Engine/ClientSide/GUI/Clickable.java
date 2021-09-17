package Engine.ClientSide.GUI;

import Engine.ClientSide.Updatable;

public interface Clickable extends Updatable {

    AABB getBoundingBox();
    void clicked();

    boolean checkClick(float mouseX, float mouseY);

}
