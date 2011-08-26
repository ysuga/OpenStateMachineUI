/**
 * DefaultStateSettingDialog.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.state;

import net.ysuga.statemachine.state.DefaultState;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.StateMachinePanel;

/**
 * @author ysuga
 *
 */
public class DefaultStateSettingDialog extends AbstractStateSettingDialog {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param state
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param state
	 * </div>
	 */
	public DefaultStateSettingDialog(StateMachinePanel panel, State state) {
		super(panel, state);
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
	protected State createState() {
		DefaultState state = new DefaultState(getStateName());
		return state;
	}

}
