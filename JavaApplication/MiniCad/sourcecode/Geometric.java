package com.cube;

import java.awt.*;

abstract class Geometric {
    Font  font;
    int LocX;
    int LocY;
    int strokeWid;
    int width;
    int height;
    int size;
    Point pointFirst;
    Point pointSecond;
    Color defaultColor;
    abstract void drawPanel(Graphics g);
    abstract boolean clickTest(Point p);
}
