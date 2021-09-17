package Engine.ClientSide.GUI;

import Engine.ClientSide.GameObject;

import java.util.Collection;

public interface Container {

    Collection<? extends GameObject> getChildren();

}
