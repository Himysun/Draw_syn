
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ellipse extends Rectangle
{
    private Color color;

    //构造方法，参数指定椭圆外切矩形的位置和大小
    public Ellipse(Point point, int length, int width)
    {
        super(point, length, width);             //其中，this.shape="矩形"
        this.shape = "椭圆";
    }
    public Ellipse(Point point1, Point point2)
    {
        super(point1, point2);
        this.shape = "椭圆";
    }
    public Ellipse(int x1,int y1, int x2,int y2,Color color)
    {
        super(x1,y1, x2,y2, color);
        this.color=color;
        this.shape = "椭圆";
    }
    public Ellipse()
    {
        super();
        this.shape = "椭圆";
    }
    public Ellipse(Ellipse elli)                 //拷贝构造方法
    {
        super(elli);                             //调用父类的拷贝构造方法
        this.shape = "椭圆";
    }

    //第6章
    public void draw(Graphics g)        //绘图，覆盖
    {
        g.setColor(this.color);
        g.drawOval(this.point1.x, this.point1.y, this.length, this.width);//参数分别指定椭圆外切矩形的左上角点坐标、长度和宽度
    }

    public String toString()                     //对象描述字符串，输出外切矩形属性。覆盖
    {
        return "，"+"外切矩形"+super.toString();    //第3章，输出"椭圆外切矩形(……)"，希望的含义
//        return this.getClass().getName()+this.shape+"(外切"+super.toString()+")";//第4章，输出"Ellipse(外切Ellipse(……)"，不希望的含义
    }

    public double perimeter()                    //计算椭圆周长，公式为π(a轴半径+b轴半径)，覆盖
    {
        return Math.PI*(this.length/2+this.width/2);
    }
    public double area()                         //计算椭圆面积，公式为πab，覆盖
    {
        return Math.PI*(this.length/2)*(this.width/2);
    }


}
