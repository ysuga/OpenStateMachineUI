/**
 * AndGuardSettingDialog.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/19
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.guard;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.guard.AndGuard;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.ui.shape.TransitionSettingDialog;

/**
 * @author ysuga
 *
 */
public class AndGuardSettingDialog extends AbstractLogicGuardSettingDialog {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param transitionSettingDialog
	 * @param guardKind
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param transitionSettingDialog
	 * @param guardKind
	 * </div>
	 */
	public AndGuardSettingDialog(
			TransitionSettingDialog transitionSettingDialog) {
		super(transitionSettingDialog, StateMachineTagNames.AND);
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
	protected int getNumChildGuard() {
		return 2;
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
	public Guard createGuard() throws InvalidGuardException {
		return new AndGuard(getGuardName(), getChildGuards());
	}

}
