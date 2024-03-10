

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Rectangle extends ClosedFigure
{
    protected int length, width;                 //矩形的长度和宽度
    private Color color;

    //构造方法，参数分别指定矩形左上角点坐标、长度和宽度。若length或width<0，抛出无效参数异常
    public Rectangle(Point point1, int length, int width) throws IllegalArgumentException
    {
        super("矩形", point1);
        this.set(length, width);
    }
    //构造方法重载，参数指定矩形左上角点和右下角点坐标
    public Rectangle(Point point1, Point point2)
    {
        this(point1, Math.abs(point2.x-point1.x), Math.abs(point2.y-point1.y));
    }
    //构造方法重载，参数(x1,y1)、(x2,y2)指定矩形左上角和右下角点坐标
    public Rectangle(int x1,int y1, int x2,int y2,Color color)
    {
        this(new Point(x1,y1), Math.abs(x2-x1), Math.abs(y2-y1));//其中，Math.abs(x)返回x的绝对值
        this.color=color;
//        this(new Point(x1,y1), new Point(x2,y2));//也可
    }
    public Rectangle()
    {
        this(new Point(),0,0);
    }
    public Rectangle(Rectangle rec)              //拷贝构造方法
    {
        this(rec.point1, rec.length, rec.width);
    }

    public void draw(java.awt.Graphics g)        //绘图
    {
        g.setColor(this.color);
        g.drawRect(this.point1.x, this.point1.y, this.length, this.width);
    }

    public String toString()                     //对象描述字符串，包括点位置、长度、宽度属性。覆盖，扩展功能
    {
        return "(左上角点"+super.toString()+"，长度"+this.length+"，宽度"+this.width+")";
    }

    public double perimeter()                    //计算矩形周长，实现父类的抽象方法
    {
        return (this.width+this.length)*2;
    }
    public double area()                         //计算矩形面积，实现父类的抽象方法
    {
        return this.width*this.length;
    }
    //以上5-0单元测验题

    //构造方法，参数分别指定矩形的长度和宽度。若length或width<0，抛出无效参数异常
    public void set(int length, int width) throws IllegalArgumentException
    {
        if(length>=0 && width>=0)
        {
            this.length = length;
            this.width = width;
        }
        else throw new IllegalArgumentException("矩形的长度或宽度不能为负数。");
    }

    public double getLength()
    {
        return this.length;
    }

    public double getWidth()
    {
        return this.width;
    }

    //【思考题3-6】
    public boolean contains(Point p)             //判断p(x,y)点是否在当前矩形区域内
    {
        return p.x>=this.point1.x && p.x<=this.point1.x+this.length &&
                p.y>=this.point1.y && p.y<=this.point1.y+this.width;
    }

    //【思考题3-6】第6章6.4图形

}


