/**
 * StateShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/11
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import net.ysuga.statemachine.state.State;

/**
 * @author ysuga
 *
 */
public interface StateShape extends ModelShape {

	public State getState();
}
