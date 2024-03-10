
import java.awt.*;
import java.io.Serializable;

public class Line extends Figure
{
    private Color color;
    public Point point2;
    public Line(Point point1,Point point2,Color color)
    {
        super(point1);
        this.point2 = point2;
        this.color=color;

    }

    public Line(int x1,int y1,int x2,int y2,Color color)
    {

        this(new Point(x1,y1), new Point(x2,y2), color);
    }

    public void draw(Graphics g)        //绘图
    {
        g.setColor(this.color);
        g.drawLine(this.point1.x, this.point1.y, this.point2.x, this.point2.y);
    }

    public double length()
    {
        int a=point1.x-point2.x,  b=point1.y-point2.y;
        return Math.sqrt(a*a+b*b);
    }

    public String toString()
    {
        return this.getClass().getName()+        //第4章4.3.1节，【思考题4-4】
                "("+super.toString()+", "+this.point2.toString()+")"+
                "，长度"+String.format("%1.2f",this.length());
    }






}
