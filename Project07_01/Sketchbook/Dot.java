package Sketchbook;

/*
 * This class defines a point (called Dot because Point is existing), extends DrawnShape
 * contains x and y coordinate
 * has methods to return required parameters for drawing
 * @author Caden Wells, Felipe Vasquez
 */

public class Dot extends DrawnShape {
	public int px, py;
	
	//constructor method for a dot
	//parameters; x and y coordanites of start and end points
	public Dot(int px, int py) {
        this.px = px;
        this.py = py;
        
        super.type = "Point";
        super.geometry = px + ", " + py;
    }
	
	public Dot(String px, String py) {
		this.px = Integer.parseInt(px);
		this.py = Integer.parseInt(py);
		
        super.type = "Point";
        super.geometry = this.px + ", " + this.py;
	}	
	//the following methods are used to return the required parameters for drawing

	public int px() {
		return px;
	}
	public int py() {
		return py;
	}
	// Outputs the point values as a string.
    public String geometry() {
    	String geometry = px + ", " + py;
    	return geometry;
    }
}
