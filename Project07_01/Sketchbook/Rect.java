package Sketchbook;

public class Rect extends DrawnShape{
	private int recX1, recX2, recY1, recY2;
	int widthRec, heightRec;
	
	public Rect(int recX1, int recY1, int recX2, int recY2) {
        this.recX1 = recX1;
        this.recY1 = recY1;
        this.recX2 = recX2;
        this.recY2 = recY2;
        
        super.type = "Rectangle";

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
		String geometry = recX1 + ", " + recX2 + ", " + recY1 + ", " + recY2;
		return geometry;
	}
	 
}
