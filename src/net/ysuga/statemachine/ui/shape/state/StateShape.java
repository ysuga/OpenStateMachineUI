/**
 * StateShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/11
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape.state;

import java.awt.event.MouseEvent;

import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.StateMachinePanel;
import net.ysuga.statemachine.ui.shape.ModelShape;

/**
 * @author ysuga
 *
 */
public interface StateShape extends ModelShape {

	public State getState();

	/**
	 * onClicked
	 * <div lang="ja">
	 * 
	 * @param panel
	 * @param arg0
	 * </div>
	 * <div lang="en">
	 *
	 * @param panel
	 * @param arg0
	 * </div>
	 */
	public void onClicked(StateMachinePanel panel, MouseEvent arg0);
}
