import java.awt.Point;

public abstract class ClosedFigure extends Figure
{
    protected String shape;
    protected ClosedFigure(String shape, Point point1)//构造方法
    {
        super(point1);                           //调用父类构造方法，初始化父类的point1
        this.shape = shape;
    }
    protected ClosedFigure(String shape)         //第5版教材没写
    {
        this(shape, new Point());
    }
    protected ClosedFigure()
    {
        this("", new Point());
    }

}