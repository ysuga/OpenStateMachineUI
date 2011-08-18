/**
 * StateSettingDialogFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;


/**
 * @author ysuga
 *
 */
public interface GuardSettingDialogFactory {

	public String getKind();
	public AbstractGuardSettingDialog createGuardSettingDialog(TransitionSettingDialog transitionSettingDialog);
}
