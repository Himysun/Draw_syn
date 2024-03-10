

import java.awt.*;
import java.io.Serializable;

public abstract class Figure implements Serializable
{
    public Point point1;
    protected Figure(Point point1)
    {
        this.point1 = point1;
    }
    public String toString()
    {
        return this.point1==null?"":this.point1.toString();
    }
    protected abstract void draw(Graphics g);

}
