package Sketchbook;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

    private ArrayList<Point> points;
    private boolean drawing;

    public DrawingPanel() {
        points = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        // Draw all lines stored in the points list
		
		for (int i = 0; i < points.size(); i++) {
			//draw a point
			Point p = points.get(i);
			g.fillOval(p.x, p.y, 5, 5);
		}
		 

        // Draw the current line being drawn (if any)
		/*
		 * if (drawing && points.size() > 0) { Point p = points.get(points.size() - 1);
		 * g.drawLine(p.x, p.y, p.x, p.y); }
		 */
    }

    // MouseListener methods
    public void mousePressed(MouseEvent e) {
        points.add(e.getPoint());
        drawing = true;
    }

    public void mouseReleased(MouseEvent e) {
        drawing = false;
        repaint();
    }

    
    // Other MouseListener methods
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            drawing = false;
            repaint();
        }
    }
    
    
    // Other MouseListener methods
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    // MouseMotionListener methods
    public void mouseDragged(MouseEvent e) {
        points.add(e.getPoint());
        repaint();
    }

    public void mouseMoved(MouseEvent e) {}
}