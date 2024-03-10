import java.awt.*;
import java.io.Serializable;

public class Rubber extends Rectangle
{
    private Color color;
    public Rubber(Point point1,Point point2,Color color)
    {
        super(point1, point2);
        this.color=color;
    }

    public Rubber(int x1,int y1,int x2,int y2,Color color)
    {
        this(new Point(x1,y1), new Point(x2,y2), color);
    }


    public void draw(java.awt.Graphics g)        //»æÍ¼
    {
        g.setColor(this.color);
        g.fillRect(this.point1.x, this.point1.y, 20, 20);
    }
    public void draw0(java.awt.Graphics g)        //»æÍ¼
    {
        g.setColor(this.color);
        g.fillRect(this.point1.x, this.point1.y, 1600, 1600);
    }


}
