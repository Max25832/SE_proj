package Sketchbook;

public class Dot {
	private int px, py;
	
	public Dot(int px, int py) {
        this.px = px;
        this.py = py;

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
