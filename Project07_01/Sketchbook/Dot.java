package Sketchbook;

public class Dot extends DrawnShape {
	public int px, py;
	
	public Dot(int px, int py) {
        this.px = px;
        this.py = py;
        
        super.type = "Point";
        super.geometry = px + ", " + py;

    }	
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