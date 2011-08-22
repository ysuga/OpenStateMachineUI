/**
 * AndGuardSettingDialogFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/19
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.guard;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.ui.shape.TransitionSettingDialog;

/**
 * @author ysuga
 *
 */
public class NotGuardSettingDialogFactory implements GuardSettingDialogFactory {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public NotGuardSettingDialogFactory() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	public String getKind() {
		return StateMachineTagNames.NOT;
	}

	/**
	 * <div lang="ja">
	 * @param transitionSettingDialog
	 * @return
	 * </div>
	 * <div lang="en">
	 * @param transitionSettingDialog
	 * @return
	 * </div>
	 */
	public AbstractGuardSettingDialog createGuardSettingDialog(
			TransitionSettingDialog transitionSettingDialog) {
		return new NotGuardSettingDialog(transitionSettingDialog);
	}

}
