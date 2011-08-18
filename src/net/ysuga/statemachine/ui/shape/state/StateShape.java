/**
 * StateShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/11
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape.state;

import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.shape.ModelShape;

/**
 * @author ysuga
 *
 */
public interface StateShape extends ModelShape {

	public State getState();
}
