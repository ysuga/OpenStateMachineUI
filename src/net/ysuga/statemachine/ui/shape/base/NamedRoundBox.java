package net.ysuga.statemachine.ui.shape.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class NamedRoundBox extends RoundRectangle2D.Double {

	static double clearance = 5;

	final private String name;

	public String getName() {
		return name;
	}

	private GlyphVector glyphVector;

	/**
	 * @return glyphVector
	 */
	public final GlyphVector getGlyphVector() {
		return glyphVector;
	}

	private Rectangle2D.Double[] anchors;

	private boolean selected = false;

	final public boolean isSelected() {
		return selected;
	}

	final public void setSelected(boolean flag) {
		selected = flag;
	}

	public final void setLocation(Point p) {
		double boxwidth = getWidth();
		double boxheight = getHeight();
		double x = p.x;
		double y = p.y;
		this.setRoundRect(x, y, boxwidth, boxheight, boxheight / 6,
				boxheight / 6);

		Rectangle b = getBounds();
		final int w = 6;
		anchors[0].setRect(b.x - w / 2, b.y - w / 2, w, w);
		anchors[1].setRect(b.x - w / 2 + b.width, b.y - w / 2, w, w);
		anchors[2].setRect(b.x - w / 2, b.y - w / 2 + b.height, w, w);
		anchors[3].setRect(b.x - w / 2 + b.width, b.y - w / 2 + b.height, w, w);
	}

	public NamedRoundBox(String name, double x, double y, double boxwidth,
			double boxheight) {
		super(x, y, boxwidth, boxheight, boxheight / 6, boxheight / 6);
		this.name = name;
		Font font = new Font(Font.SERIF, 0, 16);
		glyphVector = font.createGlyphVector(new FontRenderContext(
				new AffineTransform(), false, false), name);

		double width = getGlyphVector().getVisualBounds().getWidth();
		if (width > boxwidth + clearance * 2) {
			boxwidth = width + clearance * 2;
		}

		double w = 6;
		anchors = new Rectangle2D.Double[4];
		for (int i = 0; i < 4; i++) {
			anchors[i] = new Rectangle2D.Double(0, 0, w, w);
		}
		setLocation(new Point((int)x, (int)y));
	}

	public void draw(Graphics2D g) {
		Color colorbuf = g.getColor();
		g.setColor(new Color(255,255,220));
		g.fill(this);
		g.setColor(colorbuf);
		g.draw(this);

		Rectangle2D glyphBound = getGlyphVector().getVisualBounds();
		double x = this.getX() + this.getWidth() / 2 - glyphBound.getWidth()
				/ 2;
		double y = this.getY() + clearance + glyphBound.getHeight();
		g.drawGlyphVector(this.getGlyphVector(), (float) x, (float) y);

		if (isSelected()) {
			for (Shape selectedShape : anchors) {
				g.draw(selectedShape);
			}
		}
	}

	/**
	 * <div lang="ja">
	 * @param p
	 * @return
	 * </div>
	 * <div lang="en">
	 * @param p
	 * @return
	 * </div>
	 */

	public boolean contains(Point p) {
		return super.contains(p);
	}
}
