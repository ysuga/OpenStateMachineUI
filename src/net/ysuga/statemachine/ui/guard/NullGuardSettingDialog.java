/**
 * DelayGuardSettingDialog.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/20
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.guard;

import javax.swing.JLabel;

import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.guard.NullGuard;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;
import net.ysuga.statemachine.ui.shape.TransitionSettingDialog;

/**
 * @author ysuga
 *
 */
public class NullGuardSettingDialog extends AbstractGuardSettingDialog {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param transitionSettingDialog
	 * @param guard
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param transitionSettingDialog
	 * @param guard
	 * </div>
	 */
	public NullGuardSettingDialog(
			TransitionSettingDialog transitionSettingDialog, Guard guard) {
		super(transitionSettingDialog, guard);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * <div lang="ja">
	 * @param panel
	 * </div>
	 * <div lang="en">
	 * @param panel
	 * </div>
	 */
	@Override
	protected void initPanel(GridLayoutPanel panel) {
		panel.addComponent(0, 0, 1, 1, new JLabel("Null Guard has no setting items."));
	}

	/**
	 * <div lang="ja">
	 * @return
	 * @throws InvalidGuardException
	 * </div>
	 * <div lang="en">
	 * @return
	 * @throws InvalidGuardException
	 * </div>
	 */
	@Override
	public Guard createGuard() throws InvalidGuardException {
		return new NullGuard(getGuardName());
	}

	/**
	 * <div lang="ja">
	 * @param guard
	 * </div>
	 * <div lang="en">
	 * @param guard
	 * </div>
	 */
	@Override
	public void setDefaultSetting(Guard guard) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
