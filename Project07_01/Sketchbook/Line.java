package Sketchbook;

import java.awt.Graphics;


//A class containing Lines

public class Line {
	private int x1, y1, x2, y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    //methods to return back each element of the coordanite. There's probably a better way to do this
    public int x1() {
    	return x1;
    }
    
    public int x2() {
    	return x2;
    }
    public int y1() {
    	return y1;
    }
    public int y2() {
    	return y2;
    }
    
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawLine(10, 10, 50, 50);
//    }

    //the next two methods are just for fun, not needed
    
    public double getLength() {
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public double getSlope() {
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        return yDiff / xDiff;
    }

}