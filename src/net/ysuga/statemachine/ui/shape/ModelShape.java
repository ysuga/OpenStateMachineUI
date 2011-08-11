package net.ysuga.statemachine.ui.shape;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import net.ysuga.statemachine.state.ModelElement;

public interface ModelShape {
	
	public void draw(Graphics2D g);

	public boolean contains(Point p);
	
	public Point getCenterPoint();

	public Rectangle getBounds();
		
	public void setSelected(boolean flag);
	
	public boolean isSelected();
	
	public double getX();
	
	public double getY();
}
