/**
 * TransitionShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/10
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import net.ysuga.statemachine.transition.PivotList;
import net.ysuga.statemachine.transition.Transition;

/**
 * @author ysuga
 *
 */
public class TransitionShape {

	Transition transition;
	
	public Transition getTransition() {
		return transition;
	}
	
	boolean selected;
	
	StateShape sourceStateShape;
	StateShape targetStateShape;
	
	private PivottedNamedArrow arrow;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public TransitionShape(Transition transition, StateShape sourceStateShape, StateShape targetStateShape) {
		this.transition = transition;
		this.sourceStateShape = sourceStateShape;
		this.targetStateShape = targetStateShape;
		

		initShape();
	}
	
	private void initShape() {
		Point fromPoint = sourceStateShape.getCenterPoint();
		Point distPoint = targetStateShape.getCenterPoint();

		Rectangle2D sourceRect = sourceStateShape.getBounds();
		Rectangle2D targetRect = targetStateShape.getBounds();

		Point lastPivot;
		Point firstPivot;
		setArrow(new PivottedNamedArrow( transition.getName(), sourceStateShape.getCenterPoint(),
				targetStateShape.getCenterPoint(), transition.getPivotList()));

		if(transition.getPivotList().size() != 0) { 
			firstPivot = getArrow().getFirstPivotPoint();
			lastPivot = getArrow().getLastPivotPoint();
		}else if (sourceRect.intersects(targetRect)) { 
			// 重なっていると厄介なので，強制的にPIVOTを追加．
			getArrow().addPivot(new Point((int) getArrow().getCenterX() - 40,
					(int) getArrow().getCenterY() - 40));
			getArrow().addPivot(new Point((int) getArrow().getCenterX() + 40,
					(int) getArrow().getCenterY() - 40));
			firstPivot = getArrow().getFirstPivotPoint();
			lastPivot = getArrow().getLastPivotPoint();
			
		} else {
			firstPivot = distPoint;
			lastPivot = fromPoint;
		}

		Line2D tempLineFrom = new Line2D.Double(fromPoint, firstPivot);
		Line2D tempLineTo = new Line2D.Double(lastPivot, distPoint);
		Point fromEdge = detectEdge(sourceRect, tempLineFrom);
		Point toEdge = detectEdge(targetRect, tempLineTo);

		if (fromEdge != null && toEdge != null) {
			setArrow(new PivottedNamedArrow(transition.getName(), 
					fromEdge, toEdge, transition.getPivotList()));
		}
	}

	public void draw(Graphics2D g) {

		initShape();
		getArrow().setSelected(isSelected());
		getArrow().draw(g);
		
		
		// TODO:
		// transition.setPivotList(arrow.getPivotList());

		/*
		for (ModelShape shape : getShapeList()) {
			shape.draw(g);
		}
		*/
	}
	
	final static Point detectEdge(Rectangle2D box, Line2D line) {
		double x1 = line.getX1();
		double x2 = line.getX2();
		double y1 = line.getY1();
		double y2 = line.getY2();

		double xx = x1;
		double yy = y1;

		double u1 = box.getX();
		double u2 = box.getX() + box.getWidth();
		double v1 = box.getY();
		double v2 = box.getY() + box.getHeight();

		double a = (y2 - y1) / (x2 - x1);
		double v = 0, u = 0;

		// u1 should be less than u2
		if (u1 >= u2) {
			double buf = u1;
			u1 = u2;
			u2 = buf;
		}
		// x1 should be less than x2
		if (x1 >= x2) {
			double buf = x1;
			x1 = x2;
			x2 = buf;
		}
		// v1 should be less than v2
		if (v1 >= v2) {
			double buf = v1;
			v1 = v2;
			v2 = buf;
		}
		// y1 should be less than y2
		if (y1 >= y2) {
			double buf = y1;
			y1 = y2;
			y2 = buf;
		}

		if (u1 >= x1 && u1 <= x2) {
			// x = u1
			v = a * (u1 - xx) + yy;
			if (v >= v1 && v <= v2) {
				return new Point((int) u1, (int) v);
			}
		}

		if (u2 >= x1 && u2 <= x2) {
			// x = u2
			v = a * (u2 - xx) + yy;
			if (v >= v1 && v <= v2) {
				return new Point((int) u2, (int) v);
			}
		}

		if (v1 >= y1 && v1 <= y2) {
			// y = v1
			u = (v1 - yy) / a + xx;
			if (u >= u1 && u <= u2) {
				return new Point((int) u, (int) v1);
			}
		}

		if (v2 >= y1 && v2 <= y2) {
			// y = v2
			u = (v2 - yy) / a + xx;
			if (u >= u1 && u <= u2) {
				return new Point((int) u, (int) v2);
			}
		}

		return null;
	}

	/**
	 * <div lang="ja">
	 *
	 * @param x
	 * @param y
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param x
	 * @param y
	 * @return
	 * </div>
	 */
	public boolean contains(Point point) {
		return getArrow().contains(point);
	}

	/**
	 * @param arrow the arrow to set
	 */
	public void setArrow(PivottedNamedArrow arrow) {
		this.arrow = arrow;
	}

	/**
	 * @return the arrow
	 */
	public PivottedNamedArrow getArrow() {
		return arrow;
	}
	
	private Point selectedPivot;
	
	public void onMousePressed(MouseEvent arg0) {
		Point mp = arg0.getPoint();
		selectedPivot = null;
		//PivotList pivotList = transition.getPivotList();
		for(Point p : transition.getPivotList()) {
			double dist = Math.sqrt((p.x - mp.x) * (p.x - mp.x) + (p.y - mp.y) * (p.y - mp.y));
			if(dist < 10) {
				selectedPivot = p;
			}
		}
		if(selectedPivot == null) {
			selectedPivot = new Point(mp.x, mp.y);
			
			Point oldPoint = arrow.getStartPoint();
			for(int i = 0;i < transition.getPivotList().size();i++) {
				Point p = transition.getPivotList().get(i);
				if(PivottedNamedArrow.lineContains(oldPoint.x, oldPoint.y, p.x, p.y, mp)) {
					transition.getPivotList().add(i, selectedPivot);
					return;
				}
				oldPoint = p;
			}
			transition.getPivotList().add(selectedPivot);
		}
	}
	
	public void onMouseDragged(MouseEvent arg0) {
		Point mp = arg0.getPoint();
		//pivotList.remove(selectedPivot);
		selectedPivot.setLocation(mp.x, mp.y);
	}
	
	public void onMouseReleased(MouseEvent arg0) {
		Point prevPoint, nextPoint;
		
		PivotList pivotList = transition.getPivotList();
		int selectedIndex = pivotList.indexOf(selectedPivot);
		if(selectedIndex == 0) { // firstPivot
			prevPoint = arrow.getStartPoint();
		} else {
			prevPoint = pivotList.get(selectedIndex-1);
		}
		
		if(selectedIndex == pivotList.size()-1) { //lastPivot
			nextPoint = arrow.getEndPoint();
		} else {
			nextPoint = pivotList.get(selectedIndex+1);
		}
		if(PivottedNamedArrow.lineContains(prevPoint.x, prevPoint.y, nextPoint.x, nextPoint.y, selectedPivot)) {
			pivotList.remove(selectedPivot);
		}
		selectedPivot = null;
	}

	/**
	 * <div lang="ja">
	 *
	 * @param b
	 * </div>
	 * <div lang="en">
	 *
	 * @param b
	 * </div>
	 */
	public void setSelected(boolean b) {
		selected = b;
	}
	
	public boolean isSelected() {
		return selected;
	}
}
