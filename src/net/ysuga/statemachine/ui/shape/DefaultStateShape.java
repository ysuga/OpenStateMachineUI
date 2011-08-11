/**
 * StateShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/07
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import net.ysuga.statemachine.state.ModelElement;
import net.ysuga.statemachine.state.State;

/**
 * @author ysuga
 *
 */
public class DefaultStateShape implements StateShape {

	private NamedRoundBox box;
	private State state;
	
	static int boxwidth = 80;
	static int boxheight = 60;
	
	/**
	 * <div lang="ja">
	 * �R���X�g���N�^
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public DefaultStateShape(State state) {
		box = new NamedRoundBox(state.getName(), state.getX(), state.getY(), boxwidth, boxheight);
		this.state = state;
	}
	/**
	 * <div lang="ja">
	 *
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public Point getCenterPoint() {
		return new Point((int)box.getCenterX(), (int)box.getCenterY());
	}
	/**
	 * <div lang="ja">
	 * @param g
	 * </div>
	 * <div lang="en">
	 * @param g
	 * </div>
	 */
	@Override
	public void draw(Graphics2D g) {
		box.draw(g);
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
	@Override
	public boolean contains(Point p) {
		return box.contains(p);
	}

	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	@Override
	public Rectangle getBounds() {
		return box.getBounds();
	}
	/**
	 * <div lang="ja">
	 * @param flag
	 * </div>
	 * <div lang="en">
	 * @param flag
	 * </div>
	 */
	@Override
	public void setSelected(boolean flag) {
		box.setSelected(flag);
	}
	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	@Override
	public boolean isSelected() {
		return box.isSelected();
	}
	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	@Override
	public State getState() {
		return state;
	}

	@Override
	public double getX() {
		return box.x;
	}
	
	@Override
	public double getY() {
		return box.y;
	}
}
