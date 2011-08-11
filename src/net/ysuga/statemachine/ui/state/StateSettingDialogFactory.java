/**
 * StateSettingDialogFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.state;

import net.ysuga.statemachine.state.State;

/**
 * @author ysuga
 *
 */
public interface StateSettingDialogFactory {

	public String getKind();
	public AbstractStateSettingDialog createStateSettingDialog(State state);
}
