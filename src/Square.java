
import java.awt.Point;
import java.io.Serializable;

public final class Square extends Rectangle    //正方形类，继承矩形类，最终类，不能被继承
{
    //构造方法，point、length分别指定正方形左上角点坐标、长度
    public Square(Point point, int length)
    {
        super(point,length,length);              //正方形是矩形特例，长度和宽度相等
        this.shape = "正方形";                     //shape权限必须为protected，不能为private
    }
    public Square()
    {
        this(new Point(),0);
    }
    public String toString()
    {
        return //this.getClass().getName()+
                "(左上角点坐标"+this.point1+"，边长"+this.length+")";
    }
}
