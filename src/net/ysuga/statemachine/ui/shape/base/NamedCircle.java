package net.ysuga.statemachine.ui.shape.base;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class NamedCircle {

	private boolean selected;
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}
	
	private Ellipse2D.Double ellipse;
	
	Font font;
	
	final public Ellipse2D getEllipse() {
		return ellipse;
	}
	
	private ArrayList<Shape> selectedShapeList;
	
	final public ArrayList<Shape> getSelectedShapeList() {
		return selectedShapeList;
	}
	
	private String name;

	private GlyphVector glyphVector;
	final public String getName() {return name;}
	
	public NamedCircle(String name, int x, int y, Double radius) {
		this.name = name;
		ellipse = new Ellipse2D.Double(x-radius, y - radius, radius * 2, radius * 2);
		
		Rectangle b = getBounds();
		selectedShapeList = new ArrayList<Shape>();
		final int w = 6;
		selectedShapeList.add(new Rectangle2D.Double(b.x - w / 2, b.y - w / 2, w, w));
		selectedShapeList.add(new Rectangle2D.Double(b.x - w / 2 + b.width, b.y - w / 2, w,
				w));
		selectedShapeList.add(new Rectangle2D.Double(b.x - w / 2, b.y - w / 2 + b.height, w,
				w));
		selectedShapeList.add(new Rectangle2D.Double(b.x - w / 2 + b.width, b.y - w / 2
				+ b.height, w, w));
		
		this.font = new Font(Font.SERIF, 0, 16);
		
		
		glyphVector = font.createGlyphVector(new FontRenderContext(
						new AffineTransform(), false, false), name);
	}

	public boolean contains(Point p) {
		return ellipse.contains(p);
	}

	public double getCenterX() {
		return ellipse.getCenterX();
	}
	
	public double getCenterY() {
		return ellipse.getCenterY();
	}

	public Rectangle getBounds() {
		return ellipse.getBounds();
	}

	/**
	 * @return glyphVector
	 */
	public final GlyphVector getGlyphVector() {
		return glyphVector;
	}
	
	public void draw(Graphics2D g) {
		g.fill(getEllipse());
		
		g.drawGlyphVector(getGlyphVector(), getBounds().x, getBounds().y);
		
		
		if (isSelected()) {
			ArrayList<Shape> selectedShapeList = getSelectedShapeList();
			for (Shape selectedShape : selectedShapeList) {
				g.draw(selectedShape);
			}
		}
		
	}
}
