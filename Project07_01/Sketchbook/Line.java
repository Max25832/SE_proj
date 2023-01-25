package Sketchbook;

import java.awt.Graphics;


/*
 * This class defines a Line, extends DrawnShape
 * contains x and y coordinates
 * has methods to return required parameters for drawing
 * @author Caden Wells
 */

public class Line extends DrawnShape {
	public int x1, y1, x2, y2;

	//constructor method for a line
	//parameters; x and y coordanites of start and end points
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        
        super.type = "Line";
        super.geometry = x1 + ", " + y1 + ", " + x2 + ", " + y2;
    }
    
    public Line(String x1, String y1, String x2, String y2) {
        this.x1 = Integer.parseInt(x1);
        this.y1 = Integer.parseInt(y1);
        this.x2 = Integer.parseInt(x2);
        this.y2 = Integer.parseInt(y2);
        
        super.type = "Line";
        super.geometry = this.x1 + ", " + this.y1 + ", " + this.x2 + ", " + this.y2;
    }
    
	//the following methods are used to return the required parameters for drawing
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
    
    
    //below is just for testing
    
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
    
    public String geometry() {
    	String geometry = x1 + ", " + y1 + ", " + x2 + ", " + y2;
    	super.geometry = this.geometry;
    	return geometry;
    }

}
