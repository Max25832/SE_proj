package Sketchbook;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.border.LineBorder;

/*
 * This class describes rounded edges of the buttons 
 * it extends the border type LineBorder
 * 
 * @Author Max Kirsch
 */


class RoundedBorder extends LineBorder {

    RoundedBorder(int radius) {
        super(Color.BLACK, 2, true);
        this.radius = radius;
    }
    private int radius;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getLineColor());
        g2.setStroke(new BasicStroke(getThickness()));
        g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }


}
