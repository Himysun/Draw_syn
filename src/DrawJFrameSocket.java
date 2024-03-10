import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class DrawJFrameSocket extends JFrame implements ActionListener, ComponentListener, MouseListener, MouseMotionListener, Runnable
{
    private JButton[] buttons;
    private String[] strs= {"颜色","橡皮","清除"},str= {"直线","椭圆","矩形","随意线"};
    private JComboBox com_shape;
    protected JPopupMenu popupmenu;
    protected JMenu[] menus;
    protected JToolBar toolbar;
    private int n=strs.length;

    private Point start,end;
    private Canvas canvas;
    private Socket socket;
    private ObjectOutputStream objout;
    private Graphics g;

    private Color color0=Color.black;
    private JColorChooser color1;

    private String filename;

    private ArrayList<Figure> shaped = new ArrayList<>();

    boolean rubber = false,jude=true;
    private int x1,x2,y1,y2;

    private Thread thread;
    public DrawJFrameSocket(String name) throws IOException
    {
        //设置窗口
        super("同步画图"+name+"  "+InetAddress.getLocalHost().toString());
        //super("画图程序");
        this.setBounds(600,280,720,650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addComponentListener(this);
        //设置边布局
        this.getContentPane().setLayout(new BorderLayout());
        //设置并添加工具栏
        this.toolbar=new JToolBar("常用");
        this.buttons=new JButton[n];
        for(int i=0;i<n;i++)
        {
            toolbar.add(this.buttons[i]=new JButton(strs[i]));
            this.buttons[i].addActionListener(this);
        }
        //设置组合框
        this.com_shape=new JComboBox(str);
        this.com_shape.addActionListener(this);
        this.toolbar.add(this.com_shape);
        this.getContentPane().add(toolbar,"North");
        //设置画布
        this.start=this.end=null;
        this.canvas=new DrawCanvas();
        this.getContentPane().add(this.canvas);
        this.canvas.addMouseListener(this);
        this.canvas.addMouseMotionListener(this);
        this.objout=null;
        //this.objin=null;


        this.addMenu();
        this.setVisible(true);
    }

    public DrawJFrameSocket(String name,String host,int port) throws IOException
    {
        this(name);
        Socket socket=new Socket(host,port);
        this.setTitle(this.getTitle()+":"+socket.getLocalPort());
        this.setSocket(socket);
    }

    //设置菜单
    private void addMenu()
    {
        String[] menustr= {"文件","编辑","工具","帮助"};
        String[][] menuitemstr= {{"新建","保存","打开","另存为","退出"},{"撤销","恢复"},{"填充"},{"说明"}};
        JMenuBar menubar=new JMenuBar();
        this.setJMenuBar(menubar);
        this.menus=new JMenu[menustr.length];
        JMenuItem[][] menuitems=new JMenuItem[menuitemstr.length][];
        for(int i=0;i<menuitemstr.length;i++)
        {
            this.menus[i]=new JMenu(menustr[i]);
            menubar.add(this.menus[i]);
            menuitems[i]=new JMenuItem[menuitemstr[i].length];
            for(int j=0;j<menuitemstr[i].length;j++)
            {
                if(menuitemstr[i][j].equals("|"))
                    this.menus[i].addSeparator();
                else
                {
                    menuitems[i][j]=new JMenuItem(menuitemstr[i][j]);
                    this.menus[i].add(menuitems[i][j]);
                    menuitems[i][j].addActionListener(this);
                }
            }
        }
    }

    //监听器事件
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() instanceof JMenuItem[][]);
        {
            switch(event.getActionCommand())
            {
                case "新建":break;
                case "保存":
                {
                    filename=JOptionPane.showInputDialog(this, "文件名", "未命名");
                    if(filename!=null)
                    {
                        this.writeTo(filename, shaped);
                    }
                    System.out.print(1);break;
                }
                case "打开":
                {
                    filename=JOptionPane.showInputDialog(this, "文件名", "未命名");
                    if(filename!=null)
                    {
                        this.readFrom(filename,shaped);
                        try
                        {
                            for(int i=0;i<shaped.size();i++)
                            {
                                if(this.objout!=null)
                                    this.objout.writeObject(shaped.get(i));
                            }
                        }catch(IOException ex) {}
                    }break;
                }
                case "另存为":break;
                case "退出":System.exit(0);break;
                case "撤销":System.out.print(2);break;
                case "回复":break;
                case "清除":System.out.print(1);break;
                case "填充":System.out.print(1);break;
                case "说明":System.out.print(2);break;
            }
        }

        if(event.getSource() instanceof JButton[]);
        {
            switch(event.getActionCommand())
            {
                case "颜色":
                {
                    this.color1=new JColorChooser();
                    color0=color1.showDialog(this.buttons[0], "颜色板",Color.blue);
                    break;
                }
                case "橡皮":
                {
                    if(rubber) rubber=false;
                    else rubber = true;
                    break;
                }
                case "清除":
                {
                    Graphics g=((DrawCanvas) canvas).getGraphics();
                    g.setColor(color0);
                    Clear rub0=new Clear(0,0,0,0,getBackground());
                    rub0.draw(g);
                    try
                    {
                        if(this.objout!=null)
                        {
                            this.objout.writeObject(rub0);
                            shaped.add(rub0);
                            System.out.print(8);
                        }
                    }catch(IOException ex) {}
                    shaped.clear();
                    break;
                }
            }
        }
    }

    //鼠标点击事件
    public void mousePressed(MouseEvent event)
    {
        x1 = event.getX();
        y1 = event.getY();
        this.start=null;
        this.end=new Point(event.getX(),event.getY());
    }

    //鼠标拖动事件
    public void mouseDragged(MouseEvent e)
    {
        Graphics g=((DrawCanvas) canvas).getGraphics();
        g.setColor(color0);
        x2 = e.getX();
        y2 = e.getY();

        if (rubber)
        {
            Rubber rub=new Rubber(x2,y2, 0,0,getBackground());
            rub.draw(g);
            shaped.add(rub);
            try
            {
                if(this.objout!=null)
                    this.objout.writeObject(rub);
            }catch(IOException ex) {}
        }

        else if(this.com_shape.getSelectedItem()==str[3])
        {
            this.start=this.end;
            this.end=new Point(x2,y2);
            Line line=new Line(start,end,g.getColor());
            try
            {
                if(this.objout!=null)
                    this.objout.writeObject(line);
            }
            catch(IOException ex) {}
            line.draw(g);
            shaped.add(line);
        }
    }

    //鼠标释放事件
    public void mouseReleased(MouseEvent event)
    {
        Graphics g=((DrawCanvas) canvas).getGraphics();
        g.setColor(color0);
        x2 = event.getX();
        y2 = event.getY();
        if(!rubber)
        {
            if(this.com_shape.getSelectedItem()==str[0])
            {
                Line line=new Line(x1, y1, x2, y2,g.getColor());
                try
                {
                    if(this.objout!=null)
                        this.objout.writeObject(line);
                }
                catch(IOException ex) {}
                line.draw(g);
                shaped.add(line);
            }
            if(this.com_shape.getSelectedItem()==str[1])
            {
                Ellipse oval=new Ellipse(x1,y1, x2,y2,g.getColor());
                try
                {
                    if(this.objout!=null)
                        this.objout.writeObject(oval);
                }
                catch(IOException ex) {}
                oval.draw(g);
                shaped.add(oval);
            }
            if(this.com_shape.getSelectedItem()==str[2])
            {
                Rectangle rect=new Rectangle(x1,y1, x2,y2,g.getColor());
                try
                {
                    if(this.objout!=null)
                        this.objout.writeObject(rect);
                }
                catch(IOException ex) {}
                rect.draw(g);
                shaped.add(rect);
            }
        }
    }

    //鼠标移动事件
    public void mouseMoved(MouseEvent arg0)
    {
        if (rubber)
        {
            Toolkit kit = Toolkit.getDefaultToolkit();
            setCursor(DEFAULT_CURSOR);//设置鼠标显示样式
        }
        else
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }


    public static void main(String[] args) throws IOException
    {
        new DrawJFrameSocket("LBLDER","127.0.0.1",10011);
    }


    public void componentHidden(ComponentEvent arg0) {}
    public void componentMoved(ComponentEvent arg0) {}
    public void componentResized(ComponentEvent arg0) {}
    public void componentShown(ComponentEvent arg0) {}
    public void mouseClicked(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}


    //画布内部类
    private class DrawCanvas extends Canvas
    {
        public void paint(Graphics g)
        {
            for (int i = 0; i < shaped.size(); ++i)
            {
                shaped.get(i).draw(g);
            }
        }

        public void update(Graphics g)
        {
            this.paint(g);
        }

        //清除-背景色的填充矩形
        public void repaintrubber(int x1,int y1,int x2,int y2)
        {
            Graphics g = this.getGraphics();
            g.setColor(getBackground());
            g.fillRect(x1,y1,x2,y2);
        }

        public void repaint()
        {
            paint(g);
        }
    }

    //读取数据
    private void readFrom(String filename,ArrayList<Figure> shaped)
    {
        try
        {
            InputStream in=new FileInputStream(filename);
            ObjectInputStream objin=new ObjectInputStream(in);
            g=((DrawCanvas) canvas).getGraphics();
            ((DrawCanvas) canvas).repaintrubber(0, 0,800, 800);
            while(true)
            {
                try
                {
                    //ArrayList<Figure> shaped = new ArrayList<>();
                    Figure figure =(Figure) objin.readObject();
                    while (figure != null)
                    {
                        shaped.add(figure);
                        System.out.print(0);
                        figure.draw(g);
                        figure = (Figure) objin.readObject();
                    }
//						for (Figure i : shaped)
//						{
//						    i.draw(g);
//
//						}
                }
                catch(EOFException eof)
                {
                    break;
                }
            }
            objin.close();
            in.close();
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this, filename+"文件不存在。");
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this, "找不到指定类");
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(this, filename+"读取文件时数据错误。");
        }
    }
    //写入数据
    protected void writeTo(String filename,ArrayList<Figure> shaped)
    {
        try
        {
            OutputStream out=new FileOutputStream(filename);
            ObjectOutputStream objout=new ObjectOutputStream(out);
            for(int i=0;i<shaped.size();i++)
                objout.writeObject(shaped.get(i));
            objout.close();
            out.close();
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this,"\""+filename+"\"文件不存在。");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "写入文件时数据错误。");
        }
    }

    public void setSocket(Socket socket) throws IOException
    {
        this.socket=socket;
        this.objout=new ObjectOutputStream(this.socket.getOutputStream());
        new Thread(this).start();
    }
    //线程
    public void run()
    {
        try
        {
            ObjectInputStream objin=new ObjectInputStream(this.socket.getInputStream());
            this.g=((DrawCanvas) canvas).getGraphics();

            while(true)
            {
                try
                {
                    Figure figure =(Figure) objin.readObject();
                    while (figure != null)
                    {
                        shaped.add(figure);
                        this.shaped.add(figure);
                        figure.draw(g);
                        figure = (Figure) objin.readObject();
                    }
                }
                catch(EOFException eof)
                {
                    break;
                }
            }
            objin.close();
            this.objout.close();
            this.socket.close();
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this, filename+"文件不存在。");
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this, "找不到指定类");
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(this, "断开连接！");
        }

    }
}
