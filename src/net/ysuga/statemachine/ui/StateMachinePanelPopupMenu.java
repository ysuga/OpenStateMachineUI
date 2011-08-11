/**
 * StateMachinePopupMenu.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.state.AbstractStateSettingDialog;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactory;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactoryManager;


/**
 * @author ysuga
 *
 */
public class StateMachinePanelPopupMenu {

	private JPopupMenu popupMenu;
	
	private StateMachinePanel panel;
	
	private Point location;
	
	public Point getLocation() {
		return location;
	}
	
	class AddNewStateAction extends AbstractAction {
		private String kind;
		public AddNewStateAction(String title, String kind) {
			super(title);
			this.kind = kind;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			StateSettingDialogFactory factory = StateSettingDialogFactoryManager.getInstance().get(kind);
			AbstractStateSettingDialog dialog = factory.createStateSettingDialog(null);
			if(dialog.doModal() == AbstractStateSettingDialog.OK_OPTION) {
				State state = dialog.createState();
				try {
					state.setLocation(getLocation());
					panel.getStateMachine().add(state);
					panel.repaint();
				} catch (InvalidStateNameException e) {
					JOptionPane.showMessageDialog(null, (Object)"Invalid State Name", "Exception", JOptionPane.OK_OPTION);
				}
			}
		}	
	}
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public StateMachinePanelPopupMenu(final StateMachinePanel panel) {
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
		
		Set<String> kindSet = StateSettingDialogFactoryManager.getInstance().getKindList();
		for(String kind : kindSet) {
			JMenuItem addNewMenuItem = new JMenuItem(
					new AddNewStateAction("Add New State(" + kind + ")", kind));
			popupMenu.add(addNewMenuItem);
		}
		
		location = point;
		popupMenu.show(component, point.x, point.y);
	}


}
