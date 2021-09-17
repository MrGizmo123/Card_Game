package Engine.ClientSide.GUI;

import org.jetbrains.annotations.NotNull;

import java.awt.Point;

public class AABB {

    private Point pos;
    private Point dimensions;

    public AABB(int x, int y, int width, int height)
    {
        this.pos = new Point(x, y);

        this.dimensions = new Point(width, height);
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setDimensions(Point dimensions) {
        this.dimensions = dimensions;
    }

    public Point getPos() {
        return pos;
    }

    public Point getDimensions() {
        return dimensions;
    }

    public boolean intersect(@NotNull Point point)
    {
        boolean insideXbounds = point.x > pos.x && point.x < (pos.x + dimensions.x);
        boolean insideYbounds = point.y > pos.y && point.y < (pos.y + dimensions.y);

        boolean result = insideXbounds && insideYbounds;

        return result;
    }
}
