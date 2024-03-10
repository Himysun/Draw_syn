

import java.awt.*;


public class shape
{
    //重绘直线所需要的数据
    int startx,starty,endx,endy;
    String name;
    Color linecolor;
    //构造方法（函数），初始化
    public shape(int startx,int starty,int endx,int endy,String name,Color linecolor)
    {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.name = name;
        this.linecolor = linecolor;
    }
    //重绘的方法
    public void draw(Graphics g)
    {
        if("直线".equals(name))
        {
            g.setColor(linecolor);
            g.drawLine(startx,starty,endx,endy);
        }
        if("椭圆".equals(name))
        {
            g.setColor(linecolor);
            g.drawOval(startx,starty,endx,endy);
        }
        if("矩形".equals(name))
        {
            g.setColor(linecolor);
            g.drawRect(startx,starty,endx,endy);
        }

        if("填充矩形".equals(name))
        {
            g.setColor(linecolor);
            g.fillRect(startx,starty,endx,endy);
        }

        if("随意线".equals(name))
        {
            Point start=new Point(startx,starty);
            Point end=new Point(endx,endy);
            start=end;
            end=new Point(endx,endy);
            g.setColor(linecolor);
            g.drawLine(start.x,start.y,end.x,end.y);
        }

    }
}
