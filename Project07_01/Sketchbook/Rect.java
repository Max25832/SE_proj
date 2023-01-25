package Sketchbook;

/*
 * This class defines a rectangle, extends DrawnShape
 * contains x and y coordinates, calculates length and width
 * has methods to return required parameters for drawing
 * @author Caden Wells, Felipe Vasquez
 */

//note there is an existing java class Rectangle, but I'm making my own because of the parameters i want

public class Rect extends DrawnShape{
	public int recX1, recX2, recY1, recY2;
	int widthRec, heightRec;
	
	//constructor method for a rectangle
	//parameters; x and y coordanites
	public Rect(int recX1, int recY1, int recX2, int recY2) {
        this.recX1 = recX1;
        this.recY1 = recY1;
        this.recX2 = recX2;
        this.recY2 = recY2;
        
        super.type = "Rectangle"; //set type
        super.geometry = recX1 + ", " + recY1 + ", " + recX2 + ", " + recY2;
    }
	
	public Rect(String recX1, String recY1, String recX2, String recY2) {
        this.recX1 = Integer.parseInt(recX1);
        this.recY1 = Integer.parseInt(recY1);
        this.recX2 = Integer.parseInt(recX2);
        this.recY2 = Integer.parseInt(recY2);
        
        super.type = "Rectangle";
        super.geometry = this.recX1 + ", " + this.recY1 + ", " + this.recX2 + ", " + this.recY2;
    }
	
	public int recX1() {
		return recX1;
	}
	public int recY1() {
		return recY1;
	}
	public int widthRec() {
		widthRec = recX2 - recX1;
		return widthRec;
	}
	public int heightRec() {
		heightRec = recY2 - recY1;
		return heightRec;
	}
	public String geometry() {
		String geometry = recX1 + ", " + recY1 + ", " + recX2 + ", " + recY2;
		return geometry;
	}
	 
}
