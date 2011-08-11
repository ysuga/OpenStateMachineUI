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

/**
 * @author ysuga
 *
 */
public class DefaultStateSettingDialog extends AbstractStateSettingDialog {

	/**
	 * <div lang="ja">
	 * �R���X�g���N�^
	 * @param state
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param state
	 * </div>
	 */
	public DefaultStateSettingDialog(State state) {
		super(state);
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
	public State createState() {
		return new DefaultState(getStateName());
	}

}