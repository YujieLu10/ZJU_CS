package com.cube;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main  {


    ArrayList<Geometric> geoObj = new ArrayList();
    JFrame jFrame = new JFrame();
    PaintPanel panel = new PaintPanel();

    int clickWhich = 0;
    int selection;
    ImageIcon imgIcon;
    boolean isSel = false;
    boolean isOpen = false;
    Color color;
    String inputText = "";

    public static void main(String[] args) {
        new Main();
    }

    class PaintPanel extends JPanel {

        class Line extends Geometric {
            Line(Point x, Point y, Color c){
                pointFirst = x;
                pointSecond = y;
                defaultColor = c;
                height = (pointFirst.y - pointSecond.y);
                width = (pointFirst.x - pointSecond.x);
            }
            @Override
            void drawPanel(Graphics g) {
                g.setColor(defaultColor);
                g.drawLine(pointFirst.x, pointFirst.y, pointFirst.x - width, pointFirst.y - height);
            }
            @Override
            boolean clickTest(Point p) {
                int min_xpos = Math.min(pointFirst.x, pointSecond.x);
                int max_ypos = Math.max(pointFirst.y, pointSecond.y);
                int min_ypos = Math.min(pointFirst.y, pointSecond.y);
                int max_xpos = Math.max(pointFirst.x, pointSecond.x);
                if (p.x >= min_xpos && p.y >= min_ypos && p.x <= max_xpos && p.y <= max_ypos) {
                    panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    return true;
                } else
                    return false;
            }
        }

        class Rectangle extends Geometric {
            Rectangle(Point x, Point y, Color c) {
                strokeWid = 10;
                pointFirst = x;
                pointSecond = y;
                defaultColor = c;
                height = Math.abs(pointFirst.y - pointSecond.y);
                width = Math.abs(pointFirst.x - pointSecond.x);
            }

            @Override
            void drawPanel(Graphics g) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(defaultColor);
                Stroke stroke = new BasicStroke(strokeWid, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                g2d.setStroke(stroke);
                g2d.drawRect(Math.min(pointFirst.x, pointSecond.x), Math.min(pointFirst.y, pointSecond.y),
                        Math.abs(pointSecond.x - pointFirst.x), Math.abs(pointSecond.y - pointFirst.y));

            }

            @Override
            boolean clickTest(Point p) {
                int min_xpos = Math.min(pointFirst.x, pointSecond.x);
                int max_ypos = Math.max(pointFirst.y, pointSecond.y);
                int min_ypos = Math.min(pointFirst.y, pointSecond.y);
                int max_xpos = Math.max(pointFirst.x, pointSecond.x);
                if (p.x >= min_xpos && p.y >= min_ypos && p.x <= max_xpos && p.y <= max_ypos) {
                    panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    return true;
                } else
                    return false;
            }
        }

        class Circle extends Geometric {
            Circle(Point x, Point y, Color c) {
                strokeWid = 10;
                pointFirst = x;
                pointSecond = y;
                defaultColor = c;
                height = Math.abs(pointFirst.y - pointSecond.y);
                width = Math.abs(pointFirst.x - pointSecond.x);
            }

            @Override
            void drawPanel(Graphics g) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(defaultColor);
                Stroke stroke = new BasicStroke(strokeWid, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                g2d.setStroke(stroke);
                g2d.drawOval(Math.min(pointFirst.x, pointSecond.x), Math.min(pointFirst.y, pointSecond.y),
                        Math.abs(pointSecond.x - pointFirst.x), Math.abs(pointSecond.x - pointFirst.x));

//                Stroke stroke = new BasicStroke(strokeWid);
//                g.setStroke(stroke);
            }

            @Override
            boolean clickTest(Point p) {
                int min_xpos = Math.min(pointFirst.x, pointSecond.x);
                int max_ypos = Math.max(pointFirst.y, pointSecond.y);
                int min_ypos = Math.min(pointFirst.y, pointSecond.y);
                int max_xpos = Math.max(pointFirst.x, pointSecond.x);
                if (p.x >= min_xpos && p.y >= min_ypos && p.x <= max_xpos && p.y <= max_ypos) {
                    panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    return true;
                } else
                    return false;
            }
        }

        class Text extends Geometric {
            String str;

            //int size;
            Text(Point x, Color c, String s) {
                pointFirst = x;
                defaultColor = c;
                str = s;
                size = 10;  //TODO:
            }

            @Override
            void drawPanel(Graphics g) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(defaultColor);
                g2d.setFont(new Font("BOLD", Font.BOLD, size));
                Stroke stroke = new BasicStroke(strokeWid, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                g2d.setStroke(stroke);
                g2d.drawString(str, pointFirst.x, pointFirst.y);

            }

            @Override
            boolean clickTest(Point p) {
                if (p.x >= pointFirst.x && p.y <= pointFirst.y &&
                        p.x <= pointFirst.x + 10 * str.length() && p.y >= pointFirst.y - 10) {
                    panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    return true;
                } else
                    return false;
            }
        }

        boolean on = false;
        Point pointFirst, pointSecond, point;

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isOpen) {
                g.drawImage(imgIcon.getImage(), 0, 0, 760, 480, jFrame);
            }
            Iterator<Geometric> it = geoObj.iterator();
            while (it.hasNext()) {
                Geometric tmp = it.next();
                g.setColor(tmp.defaultColor);
                tmp.drawPanel(g);
            }
            if (on) {
                if (clickWhich == 1) {
                    Line tmp1 = new Line(pointFirst, point, color);
                    tmp1.drawPanel(g);
                }
                if (clickWhich == 2) {
                    Circle tmp1 = new Circle(pointFirst, point, color);
                    tmp1.drawPanel(g);
                }
                if (clickWhich == 3) {
                    Rectangle tmp1 = new Rectangle(pointFirst, point, color);
                    tmp1.drawPanel(g);
                }
                if (clickWhich == 4) {
                }
            }

        }

        PaintPanel() {
            color = Color.BLACK;
            setBackground(Color.WHITE);
            setFocusable(true);

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    on = !on;
                    if (clickWhich == 0) {
                        Iterator<Geometric> it = geoObj.iterator();
                        int i = 0;
                        isSel = false;
                        while (it.hasNext()) {
                            Geometric tmp = it.next();
                            if (tmp.clickTest(e.getPoint())) {
                                selection = i;
                                isSel = true;
                            }
                            i++;
                        }
                        on = false;
                    }
                    if (clickWhich == 1) {
                        if (on)
                            pointFirst = e.getPoint();
                        else {
                            pointSecond = e.getPoint();
                            Line tmp = new Line(pointFirst, pointSecond, color);
                            geoObj.add(tmp);
                            repaint();
                        }
                    }
                    if (clickWhich == 2) {
                        if (on)
                            pointFirst = e.getPoint();
                        else {
                            pointSecond = e.getPoint();
                            Circle tmp = new Circle(pointFirst, pointSecond, color);
                            geoObj.add(tmp);
                            repaint();
                        }
                    }
                    if (clickWhich == 3) {
                        if (on)
                            pointFirst = e.getPoint();
                        else {
                            pointSecond = e.getPoint();
                            Rectangle tmp = new Rectangle(pointFirst, pointSecond, color);
                            geoObj.add(tmp);
                            repaint();
                        }
                    }
                    if (clickWhich == 4) {
                        if (on)
                            pointFirst = e.getPoint();
                        else {
                            pointSecond = e.getPoint();
                            Text drawText = new Text(point, color, inputText);
                            geoObj.add(drawText);
                            repaint();
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(geoObj.size() > 1 && e.getButton() == MouseEvent.BUTTON3)
                    {
                        if(geoObj.get(selection) instanceof Text)
                        {
                            geoObj.get(selection).defaultColor = Color.white;
                            repaint();
                        }
                        else {
                            geoObj.get(selection).pointFirst.x = 0;
                            geoObj.get(selection).pointFirst.y = 0;
                            geoObj.get(selection).width = 0;
                            geoObj.get(selection).height = 0;
                            geoObj.get(selection).size = 0;
                            geoObj.get(selection).pointSecond.x = 0;
                            geoObj.get(selection).pointSecond.y = 0;
                            repaint();
                        }
                    }
                }
            });
            this.requestFocus();
            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    point = e.getPoint();
                    if (on)
                        repaint();
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (clickWhich == 0 && isSel) {
                        int X = e.getX();
                        int Y = e.getY();
                        if (!(geoObj.get(selection) instanceof Text)) {
                            if (geoObj.get(selection) instanceof Line) {
                                geoObj.get(selection).pointFirst.x = X;
                                geoObj.get(selection).pointFirst.y = Y;
                            } else {
                                geoObj.get(selection).pointFirst.x = X;
                                geoObj.get(selection).pointFirst.y = Y;
                                geoObj.get(selection).pointSecond.x = X + geoObj.get(selection).width;
                                geoObj.get(selection).pointSecond.y = Y + geoObj.get(selection).height;
                            }
                        } else {
                            geoObj.get(selection).pointFirst.x = X;
                            geoObj.get(selection).pointFirst.y = Y;
                        }
                        repaint();
                    }
                }
            });
            this.requestFocus();
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    switch (e.getKeyCode())
                    {
                        case KeyEvent.VK_DOWN:geoObj.get(selection).strokeWid++;break;
                        case KeyEvent.VK_UP:geoObj.get(selection).strokeWid--;break;
                        default:break;
                    }
                    repaint();
                }
            });
            this.setFocusable(true);
            this.requestFocusInWindow();
            //this.requestFocus();
            this.addMouseWheelListener(new MouseWheelListener()
            {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e)
                {
                    if (clickWhich == 0 && isSel)
                    {
                        if (e.getWheelRotation() > 0)
                        {
                            if ((geoObj.get(selection) instanceof Text))
                            {
                                geoObj.get(selection).size++;
                            } else
                                {
                                geoObj.get(selection).pointSecond.x++;
                                geoObj.get(selection).pointSecond.y++;
                                geoObj.get(selection).height++;
                                geoObj.get(selection).width++;
                            }
                        }
                        else
                            {
                            if ((geoObj.get(selection) instanceof Text))
                            {
                                geoObj.get(selection).size--;
                            } else
                                {
                                geoObj.get(selection).pointSecond.x--;
                                geoObj.get(selection).pointSecond.y--;
                                geoObj.get(selection).height--;
                                geoObj.get(selection).width--;
                            }
                        }
                    }
                    repaint();
                }
            });
            this.requestFocus();
        }
    }


    public Main() {
        jFrame.setLayout(new BorderLayout(5, 10));
        /**
         * Menu Bar
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        menuBar.add(fileMenu);

        JMenuItem openFile = new JMenuItem("Open");
        fileMenu.add(openFile);
        JMenuItem saveFile = new JMenuItem("Save");
        fileMenu.add(saveFile);
        /**
         * open file
         */
        openFile.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser filechooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "CAD Images", "cad","jpg", "gif", "png");
                        filechooser.setFileFilter(filter);
                        int returnVal = filechooser.showOpenDialog(null);
                        String file;
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            file = filechooser.getSelectedFile().getAbsolutePath();
                            isOpen = true;
                            imgIcon = new ImageIcon(file);
                            geoObj.clear();
                            panel.repaint();
                        }
                    }
                }
        );
        /**
         * save file
         */
        saveFile.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String file;
                        JFileChooser filechooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "CAD Images", "cad","jpg", "gif", "png");
                        filechooser.setFileFilter(filter);
                        int returnVal = filechooser.showSaveDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            file = filechooser.getSelectedFile().getAbsolutePath();
                            Dimension imageSize = panel.getSize();
                            BufferedImage image = new BufferedImage(imageSize.width,
                                    imageSize.height, BufferedImage.TYPE_INT_ARGB);
                            Graphics g = image.createGraphics();
                            panel.paint(g);
                            g.dispose();
                            try {
                                ImageIO.write(image, "png", new File(file));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                }
        );

        jFrame.add(menuBar, BorderLayout.NORTH);    //容器上方放置菜单栏

        /**
         * choose the paint tool
         */
        JPanel paintTool = new JPanel();
        /**
         * select mode
         */
        JButton selMode = new JButton();
        java.net.URL imgURL = Main.class.getResource("select.png");
        ImageIcon icon_sel = new ImageIcon(imgURL);
        //ImageIcon icon_sel = new ImageIcon("icon/select.png");
        selMode.setPreferredSize(new Dimension(60, 60));
        Image selImg = icon_sel.getImage().getScaledInstance(60,60,icon_sel.getImage().SCALE_DEFAULT);
        icon_sel = new ImageIcon(selImg);
        selMode.setIcon(icon_sel);
        selMode.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        clickWhich = 0;
                        panel.repaint();
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        /**
         * draw line
         */
        JButton drawLine = new JButton();
        java.net.URL imgURLline = Main.class.getResource("line.png");
        ImageIcon icon_line = new ImageIcon(imgURLline);
        //ImageIcon icon_line = new ImageIcon("icon/line.png");
        drawLine.setPreferredSize(new Dimension(60, 60));
        Image lineImg = icon_line.getImage().getScaledInstance(60, 60, icon_line.getImage().SCALE_DEFAULT);
        icon_line = new ImageIcon(lineImg);
        drawLine.setIcon(icon_line);
        drawLine.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickWhich = 1;
                        panel.repaint();
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        /**
         * draw circle
         */
        JButton drawcircle = new JButton();
        java.net.URL imgURLcircle = Main.class.getResource("circle.png");
        ImageIcon icon_circle = new ImageIcon(imgURLcircle);
        //ImageIcon icon_circle = new ImageIcon("icon/circle.png");
        drawcircle.setPreferredSize(new Dimension(60, 60));
        Image circleImg = icon_circle.getImage().getScaledInstance(60, 60, icon_circle.getImage().SCALE_DEFAULT);
        icon_circle = new ImageIcon(circleImg);
        drawcircle.setIcon(icon_circle);
        drawcircle.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickWhich = 2;
                        panel.repaint();
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        /**
         * draw rectangle
         */
        JButton drawrec = new JButton();
        java.net.URL imgURLrec = Main.class.getResource("rectangle.png");
        ImageIcon icon_rec = new ImageIcon(imgURLrec);
        //ImageIcon icon_rec = new ImageIcon("icon/rectangle.png");
        drawrec.setPreferredSize(new Dimension(60, 60));
        Image recImg = icon_rec.getImage().getScaledInstance(60, 60, icon_rec.getImage().SCALE_DEFAULT);
        icon_rec = new ImageIcon(recImg);
        drawrec.setIcon(icon_rec);
        drawrec.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickWhich = 3;
                        panel.repaint();
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        /**
         * draw text
         */
        JButton drawtext = new JButton();
        java.net.URL imgURLpen = Main.class.getResource("write.png");
        ImageIcon icon_text = new ImageIcon(imgURLpen);
        //ImageIcon icon_text = new ImageIcon("icon/write.png");
        drawtext.setPreferredSize(new Dimension(60, 60));
        Image textImg = icon_text.getImage().getScaledInstance(60, 60, icon_text.getImage().SCALE_DEFAULT);
        icon_text = new ImageIcon(textImg);
        drawtext.setIcon(icon_text);
        drawtext.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //TODO:弹出input框
                        inputText = JOptionPane.showInputDialog("Input Text : ");
                        clickWhich = 4;
                        panel.repaint();
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        /**
         * color choose panel
         */
        //color
        JButton black = new JButton();
        black.setBackground(Color.BLACK);
        black.setPreferredSize(new Dimension(20, 20));
        black.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        color = Color.BLACK;
                        if (clickWhich == 0 && isSel){
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton white = new JButton();
        white.setBackground(Color.WHITE);
        white.setPreferredSize(new Dimension(20, 20));
        white.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        color = Color.WHITE;
                        if (clickWhich == 0 && isSel){
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton red = new JButton();
        red.setBackground(Color.RED);
        red.setPreferredSize(new Dimension(20, 20));
        red.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.red;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton green = new JButton();
        green.setBackground(Color.GREEN);
        green.setPreferredSize(new Dimension(20, 20));
        green.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.green;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton blue = new JButton();
        blue.setBackground(Color.BLUE);
        blue.setPreferredSize(new Dimension(20, 20));
        blue.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.blue;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton yellow = new JButton();
        yellow.setBackground(Color.YELLOW);
        yellow.setPreferredSize(new Dimension(20, 20));
        yellow.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.yellow;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton pink = new JButton();
        pink.setBackground(Color.pink);
        pink.setPreferredSize(new Dimension(20, 20));
        pink.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.pink;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton cyan = new JButton();
        cyan.setBackground(Color.cyan);
        cyan.setPreferredSize(new Dimension(20, 20));
        cyan.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.cyan;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JButton orange = new JButton();
        orange.setBackground(Color.orange);
        orange.setPreferredSize(new Dimension(20, 20));
        orange.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        color = Color.orange;
                        if (clickWhich == 0 && isSel)
                        {
                            geoObj.get(selection).defaultColor = color;
                            panel.repaint();
                        }
                    }
                }
        );
        panel.requestFocus();
        panel.setFocusable(true);
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(0, 3));

        colorPanel.add(black);
        colorPanel.add(white);
        colorPanel.add(red);
        colorPanel.add(orange);
        colorPanel.add(pink);
        colorPanel.add(cyan);
        colorPanel.add(green);
        colorPanel.add(blue);
        colorPanel.add(yellow);

        panel = new PaintPanel();
        jFrame.add(panel, BorderLayout.CENTER);

                panel.setFocusable(true);
                panel.requestFocusInWindow();
//            }
//        };
//        SwingUtilities.invokeLater(r);
        KeyListener keylistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                //System.out.println("key>>>"+e.getKeyCode());
                switch (e.getKeyCode())
                {
                    case 61:{
                        geoObj.get(selection).strokeWid++;
                  //      System.out.println("++>>>");
                    }break;
                    case 45:{
                        geoObj.get(selection).strokeWid--;
                  //      System.out.println("-->>>");
                    }
                    case 46: {
                        if ((geoObj.get(selection) instanceof PaintPanel.Text))
                        {
                            geoObj.get(selection).size++;
                        } else
                        {
                            geoObj.get(selection).pointSecond.x++;
                            geoObj.get(selection).pointSecond.y++;
                            geoObj.get(selection).height++;
                            geoObj.get(selection).width++;
                        }
                    }break;
                    case 44:{
                        if ((geoObj.get(selection) instanceof PaintPanel.Text))
                        {
                            geoObj.get(selection).size--;
                        } else
                        {
                            geoObj.get(selection).pointSecond.x--;
                            geoObj.get(selection).pointSecond.y--;
                            geoObj.get(selection).height--;
                            geoObj.get(selection).width--;
                        }
                    }break;
                    default:break;
                }
                panel.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        selMode.addKeyListener(keylistener);
        drawLine.addKeyListener(keylistener);
        drawcircle.addKeyListener(keylistener);
        drawrec.addKeyListener(keylistener);
        drawtext.addKeyListener(keylistener);
        black.addKeyListener(keylistener);
        white.addKeyListener(keylistener);
        red.addKeyListener(keylistener);
        orange.addKeyListener(keylistener);
        pink.addKeyListener(keylistener);
        cyan.addKeyListener(keylistener);
        green.addKeyListener(keylistener);
        blue.addKeyListener(keylistener);
        yellow.addKeyListener(keylistener);

        //panel.setFocusable(true);
        jFrame.setSize(600, 600);


        paintTool.setLayout(new GridLayout(0, 1));
        paintTool.add(selMode);
        paintTool.add(drawLine);
        paintTool.add(drawrec);
        paintTool.add(drawtext);
        paintTool.add(drawcircle);
        paintTool.add(colorPanel);

        jFrame.add(paintTool, BorderLayout.EAST);
        jFrame.setTitle("minicad");
        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

    }
//
//    static class KeyboardPanel extends JPanel implements KeyEventDispatcher
//    {
//        private int x = 100;
//        private int y = 100;
//        private char keychar = 'A';
//        public KeyboardPanel(){
//            addKeyListener(new KeyAdapter() {
//                @Override
//                public void keyPressed(KeyEvent e) {
//                    super.keyPressed(e);
//                    switch (e.getKeyCode())
//                    {
//                        case KeyEvent.VK_DOWN:y+=10;break;
//                        case KeyEvent.VK_UP:y-=10;break;
//                        case KeyEvent.VK_LEFT:x-=10;break;
//                        case KeyEvent.VK_RIGHT:x+=10;break;
//                        default:keychar = e.getKeyChar();
//                    }
//                    repaint();
//                }
//            });
//            this.requestFocus();
//        }
//        protected void paintComponent(Graphics g)
//        {
//            super.paintComponent(g);
//            g.setFont(new Font("time",Font.PLAIN, 24));
//            g.drawString(String.valueOf(keychar),x,y);
//        }
//
//        @Override
//        public boolean dispatchKeyEvent(KeyEvent e) {
//            return false;
//        }
//    }

}