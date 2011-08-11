/**
 * StateMachinePopupMenu.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.state;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.StateMachinePanel;


/**
 * @author ysuga
 *
 */
public class StatePopupMenu {

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
	public StatePopupMenu(final StateMachinePanel panel) {
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
						StateSettingDialogFactory factory = StateSettingDialogFactoryManager.getInstance().get(panel.getSelectedState().getKind());
						if(factory != null) {
							AbstractStateSettingDialog dialog = factory.createStateSettingDialog(panel.getSelectedState()); 
							if( dialog.doModal() == AbstractStateSettingDialog.OK_OPTION ) {
								State state = dialog.createState();
								try {
									panel.getStateMachine().replace(panel.getSelectedState(), state);
									panel.setSelectedState(state);
									panel.repaint();
								} catch (InvalidStateNameException ex) {
									JOptionPane.showMessageDialog(null, (Object)"Invalid State Name", "Exception", JOptionPane.OK_OPTION);
								}
							}
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
		panel.getStateMachine().remove(panel.getSelectedState());
		panel.repaint();
	}	


	
	

}
