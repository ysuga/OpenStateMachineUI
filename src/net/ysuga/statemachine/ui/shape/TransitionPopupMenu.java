/**
 * StateMachinePopupMenu.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.StateMachinePanel;
import net.ysuga.statemachine.ui.state.AbstractStateSettingDialog;


/**
 * @author ysuga
 *
 */
public class TransitionPopupMenu {

	private JPopupMenu popupMenu;
	
	private StateMachinePanel panel;
	
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public TransitionPopupMenu(final StateMachinePanel panel) {
		this.panel = panel;
		
	}
	
	/**
	 * <div lang="ja">
	 *
	 * @param point
	 * </div>
	 * <div lang="en">
	 *
	 * @param point
	 * </div>
	 */
	public void show(Component component, Point point) {
		popupMenu = new JPopupMenu();
		JMenuItem settingMenuItem = new JMenuItem(
				new AbstractAction("Setting"){
					public void actionPerformed(ActionEvent arg0) {
						TransitionSettingDialog dialog =  new TransitionSettingDialog(panel, panel.getSelectedTransition()); 
						if( dialog.doModal() == AbstractStateSettingDialog.OK_OPTION ) {
							try {
								Transition transition = panel.getSelectedTransition();
								//if (this.transition != null) {
									transition.getSourceState().removeTransition(transition.getName());
								//}
								
								dialog.createTransition();
							} catch (InvalidConnectionException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
								Object obj = "InvalidConnectionException";
								JOptionPane.showMessageDialog(panel, obj);
							}
							panel.repaint();
						}
					}					
				});
		
		popupMenu.add(settingMenuItem);
		JMenuItem deleteMenuItem = new JMenuItem(
				new AbstractAction("Delete"){
					public void actionPerformed(ActionEvent arg0) {
						onDelete();
					}
				});
		popupMenu.add(deleteMenuItem);
		
		popupMenu.show(component, point.x, point.y);
	}

	/**
	 * <div lang="ja">
	 *
	 * @param panel
	 * </div>
	 * <div lang="en">
	 *
	 * @param panel
	 * </div>
	 */
	private void onDelete() {
		Transition transition = panel.getSelectedTransition();
		transition.getSourceState().removeTransition(transition.getName());
		panel.repaint();
	}	


	
	

}
