/**
 * StateShape.java
 * as;dlfkj; a;kdjfadif  kjkasf lk aldfi lkasdfl jlajsd fl l l lj aldjf ija ljsdlfk lajsfd l l l jfiasld j
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/07
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.StateMachinePanel;
import net.ysuga.statemachine.ui.shape.base.NamedRoundBox;
import net.ysuga.statemachine.ui.state.AbstractStateSettingDialog;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactory;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactoryManager;

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
	 * <div lang="ja"> コンストラクタ </div> <div lang="en"> Constructor </div>
	 */
	public DefaultStateShape(State state) {
		Point p = state.getLocation();
		box = new NamedRoundBox(state.getName(), p.x, p.y,
				boxwidth, boxheight);
		this.state = state;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * 
	 * @return </div>
	 */
	public Point getCenterPoint() {
		return new Point((int) box.getCenterX(), (int) box.getCenterY());
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param g
	 *            </div> <div lang="en">
	 * @param g
	 *            </div>
	 */
	@Override
	public void draw(Graphics2D g) {
		box.draw(g);
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param p
	 * @return </div> <div lang="en">
	 * @param p
	 * @return </div>
	 */
	@Override
	public boolean contains(Point p) {
		return box.contains(p);
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	@Override
	public Rectangle getBounds() {
		return box.getBounds();
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param flag
	 *            </div> <div lang="en">
	 * @param flag
	 *            </div>
	 */
	@Override
	public void setSelected(boolean flag) {
		box.setSelected(flag);
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	@Override
	public boolean isSelected() {
		return box.isSelected();
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
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

	@Override
	public State getOwnerState() {
		return state;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param panel
	 * @param arg0
	 *            </div> <div lang="en">
	 * @param panel
	 * @param arg0
	 *            </div>
	 */
	public void onClicked(StateMachinePanel panel, MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON3) { // RightClick
			panel.getStatePopupMenu().show(panel, arg0.getPoint());
		}

		if (arg0.getButton() == MouseEvent.BUTTON1 && arg0.getClickCount() == 2) { // onDoubleClicked

			StateSettingDialogFactory factory = StateSettingDialogFactoryManager
					.getInstance().get(panel.getSelectedState().getKind());
			if (factory != null) {
				AbstractStateSettingDialog dialog = factory
						.createStateSettingDialog(panel.getSelectedState());
				if (dialog.doModal() == AbstractStateSettingDialog.OK_OPTION) {
					State state = dialog.buildState();
					try {
						panel.getStateMachine().replace(
								panel.getSelectedState(), state);
						panel.setSelectedState(state);
						panel.repaint();
					} catch (InvalidStateNameException ex) {
						JOptionPane.showMessageDialog(null,
								(Object) "Invalid State Name", "Exception",
								JOptionPane.OK_OPTION);
					}
				}
			}
		}
	}
}
