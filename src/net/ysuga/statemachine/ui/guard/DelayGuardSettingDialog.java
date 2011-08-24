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
import javax.swing.JTextField;

import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.guard.DelayGuard;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;
import net.ysuga.statemachine.ui.shape.TransitionSettingDialog;

/**
 * @author ysuga
 *
 */
public class DelayGuardSettingDialog extends AbstractGuardSettingDialog {

	private JTextField delayField;
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
	public DelayGuardSettingDialog(
			TransitionSettingDialog transitionSettingDialog, Guard guard) {
		super(transitionSettingDialog, guard);
		// TODO 自動生成されたコンストラクター・スタブ
		delayField = new JTextField("0");
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
		panel.addComponent(0, 0, 1, 1, new JLabel("Delay (ms)"));
		panel.addComponent(1, 0, 10, 0, 1, 1, delayField);
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
		return new DelayGuard(getGuardName(), Integer.parseInt(delayField.getText()));
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
		delayField.setText(Long.toString(((DelayGuard)guard).getInterval()));
	}

}
