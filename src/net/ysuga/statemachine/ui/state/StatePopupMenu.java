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
import javax.swing.JSeparator;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.state.StateCondition;
import net.ysuga.statemachine.ui.StateMachinePanel;


/**
 * @author ysuga
 *
 */
public class StatePopupMenu {

	private JPopupMenu popupMenu;
	
	private StateMachinePanel panel;
	
	
	private JMenuItem setInitialActiveMenuItem;

	private JMenuItem setInitialInactiveMenuItem;
	
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
							AbstractStateSettingDialog dialog = factory.createStateSettingDialog(panel, panel.getSelectedState()); 
							if( dialog.doModal() == AbstractStateSettingDialog.OK_OPTION ) {
								State state = dialog.buildState();
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
		
		popupMenu.add(new JSeparator());
		
		setInitialActiveMenuItem = new JMenuItem(new AbstractAction("Set Initial State Active") {
			public void actionPerformed(ActionEvent e) {
				panel.getSelectedState().setInitialStateCondition(StateCondition.ACTIVE);
				panel.repaint();
			}
		});
		popupMenu.add(setInitialActiveMenuItem);
		setInitialInactiveMenuItem = new JMenuItem(new AbstractAction("Set Initial State Inactive") {
			public void actionPerformed(ActionEvent e) {
				panel.getSelectedState().setInitialStateCondition(StateCondition.INACTIVE);
				panel.repaint();
			}
		});
		popupMenu.add(setInitialInactiveMenuItem);
		if(panel.getSelectedState().getInitialStateCondition().equals(StateCondition.ACTIVE)) {
			setInitialActiveMenuItem.setEnabled(false);
		} else {
			setInitialInactiveMenuItem.setEnabled(false);
		}
		
		popupMenu.add(new JSeparator());
		
		JMenuItem addTransitionMenuItem = new JMenuItem(
				new AbstractAction("Add Transition") {
					public void actionPerformed(ActionEvent e) {
						onAddTransition();
					}
				});
		popupMenu.add(addTransitionMenuItem);
		
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


	private void onAddTransition() {
		if(panel.getSelectedState().getName().equals(StateMachineTagNames.START)) {
			if(panel.getSelectedState().getNumTransition() > 0) {
				JOptionPane.showMessageDialog(panel, "Start State cannot have prural transitions");
				return;
			}
		}
		if(panel.getSelectedState().getName().equals(StateMachineTagNames.EXIT)) {
			JOptionPane.showMessageDialog(panel, "Exit State cannot have any transitions");
			return;
		}
		panel.setEditMode(StateMachinePanel.EDIT_TRANSITION);
		/**
		TransitionSettingDialog dialog =  new TransitionSettingDialog(panel); 
		dialog.setSourceStateName(panel.getSelectedState().getName());
		if( dialog.doModal() == AbstractStateSettingDialog.OK_OPTION ) {
			try {
				dialog.createTransition();
			} catch (InvalidConnectionException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				Object obj = "InvalidConnectionException";
				JOptionPane.showMessageDialog(panel, obj);
			}
			panel.repaint();
		}
		*/
	}
	

}
