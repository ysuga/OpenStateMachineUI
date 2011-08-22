/**
 * StartStateShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/11
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.StateMachinePanel;
import net.ysuga.statemachine.ui.shape.base.NamedCircle;

/**
 * @author ysuga
 *
 */
public class StartStateShape implements StateShape {

	private NamedCircle namedCircle;
	
	private State state;
	
	@Override
	public State getState() {
		return state;
	}
	
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param name
	 * @param x
	 * @param y
	 * @param radius
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param name
	 * @param x
	 * @param y
	 * @param radius
	 * </div>
	 */
	public StartStateShape(State state) {
		namedCircle = new NamedCircle(state.getName(), state.getX()+10, state.getY()+10, 10.0);
		this.state = state;
	}

	
	@Override
	public double getX() {
		return namedCircle.getBounds().x;
	}
	
	@Override
	public double getY() {
		return namedCircle.getBounds().y;
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
		namedCircle.draw(g);
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
	public Point getCenterPoint() {
		return  new Point((int)namedCircle.getCenterX(), (int)namedCircle.getCenterY());
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
		return namedCircle.contains(p);
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
		return namedCircle.getBounds();
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
		namedCircle.setSelected(flag);
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
		return namedCircle.isSelected();
	}

	@Override
	public State getOwnerState() {
		return state;
	}

	/**
	 * <div lang="ja">
	 * @param panel
	 * @param arg0
	 * </div>
	 * <div lang="en">
	 * @param panel
	 * @param arg0
	 * </div>
	 */
	public void onClicked(StateMachinePanel panel, MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON3) { // RightClick
			panel.getStatePopupMenu().show(panel, arg0.getPoint());
		}
	}
}
