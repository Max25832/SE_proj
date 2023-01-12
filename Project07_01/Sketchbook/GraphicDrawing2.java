package Sketchbook;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

public class GraphicDrawing2 extends JPanel implements MouseListener, MouseMotionListener { 
	Point sPoint = new Point(-1, -1); 
	Point ePoint = new Point(-1, -1); 
	Vector points = new Vector(); 
	
	public GraphicDrawing2(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/*

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicDrawing2 line= new GraphicDrawing2();
					JFrame f= new JFrame();
					f.setTitle("Geometry Drawing Panel");
					f.setBounds(450, 190, 500, 500);
					f.getContentPane().add(line);
					f.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// TODO Auto-generated method stub
		
	}
	*/
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		e.consume(); 
		sPoint.x = e.getX(); 
		sPoint.y = e.getY(); 
		points.addElement(new Point(sPoint)); 
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		e.consume(); 
		ePoint.x = e.getX(); 
		ePoint.y = e.getY(); 
		points.addElement(new Point(ePoint)); 
	    repaint(); 
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		e.consume();  
		ePoint.x = e.getX();  
		ePoint.y = e.getY();   
	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p = e.getPoint();
		System.out.println(p);
		for(int i = 0; i < points.size();i++){
			Point p1=(Point)points.elementAt(i);
		     if(p1.getX()==p.getX() && p1.getY()==p.getY()){ 
		
		    	 System.out.println(points);
		    	 System.out.println("clicked");
		    	 points.remove(i); 
		    	 System.out.println(points);
		    	 repaint();
		    	 }
		     }
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		Point p1, p2; 
		/* Draw old lines.Every 2 points construct a line.*/
	    for (int i=0; i < points.size()-1; i+=2) { 
		p1 = (Point)points.elementAt(i); 
		p2 = (Point)points.elementAt(i+1); 
		g.drawLine(p1.x, p1.y, p2.x, p2.y); 
		} 
		/* Draw current line.*/
		g.drawLine(sPoint.x, sPoint.y, ePoint.x, ePoint.y); 
	}
}