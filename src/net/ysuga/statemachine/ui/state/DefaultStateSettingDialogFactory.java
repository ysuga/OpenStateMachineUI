/**
 * DefaultStateSettingFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.state;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;

/**
 * @author ysuga
 *
 */
public class DefaultStateSettingDialogFactory implements StateSettingDialogFactory {

	private String kind;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public DefaultStateSettingDialogFactory() {
		kind = StateMachineTagNames.DEFAULT_STATE;
	}

	/**
	 * <div lang="ja">
	 * @param state
	 * @return
	 * </div>
	 * <div lang="en">
	 * @param state
	 * @return
	 * </div>
	 */
	@Override
	public AbstractStateSettingDialog createStateSettingDialog(State state) {
		return new DefaultStateSettingDialog(state);
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
	public String getKind() {
		return kind;
	}

}
