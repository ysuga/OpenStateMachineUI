/**
 * LogicGuardSettingDialog.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/19
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.guard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.guard.GuardFactoryManager;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;
import net.ysuga.statemachine.ui.shape.TransitionSettingDialog;


/**
 * @author ysuga
 *
 */
public abstract class AbstractLogicGuardSettingDialog extends AbstractGuardSettingDialog {

	JComboBox[] guardKindComboBox;
	JButton[] guardSettingButton;
	Guard[] childGuards;
	
	public Guard[] getChildGuards() {
		return childGuards;
	}
	
	
	class GuardSettingButtonAction extends AbstractAction { 
		int index;
		GuardSettingButtonAction(String title, int index) {
			super(title);
			this.index = index;
		}
		
		public void actionPerformed(ActionEvent e) {
			String kind = (String) guardKindComboBox[index].getSelectedItem();
			GuardSettingDialogFactory factory = GuardSettingDialogFactoryManager
					.getInstance()
					.get(kind);
			if (factory == null) {
				JOptionPane.showMessageDialog(getTransitionSettingDialog().getStateMachinePanel(), "Guard Kind(" + guardKindComboBox[index].getSelectedItem() + ") is not properly registered");
			}
			AbstractGuardSettingDialog guardSettingDialog = factory.createGuardSettingDialog(getTransitionSettingDialog());
			if(guardSettingDialog.doModal() == AbstractGuardSettingDialog.OK_OPTION) {
				
				try {
					childGuards[index] = guardSettingDialog.createGuard();
					
					boolean initializeOKFlag = true; 
					for(int i = 0;i < childGuards.length;i++) {
						if(childGuards[i] == null) initializeOKFlag = false;
					}
					if(initializeOKFlag) {
						getOKButton().setEnabled(true);
					}
				} catch (InvalidGuardException e1) {
					// TODO  チャイルドガードが生成失敗に終わった場合はどうするか・・・
					e1.printStackTrace();
					childGuards[index] = null;
				}
				
			}
		}
	}
	
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
	public AbstractLogicGuardSettingDialog(
			TransitionSettingDialog transitionSettingDialog, String guardKind) {
		super(transitionSettingDialog, null);
		setTitle("LogicGuardSettingDialog(" + guardKind + ")");

		int numChildGuard = this.getNumChildGuard();
		childGuards = new Guard[numChildGuard];
		guardKindComboBox = new JComboBox[numChildGuard];
		guardSettingButton = new JButton[numChildGuard];
		
		@SuppressWarnings("unchecked")
		Set<String> kindSet = GuardFactoryManager.getInstance().getKindSet();

		for(int i = 0;i < numChildGuard;i++) {
//			guardNameField[i] = new JTextField("guard"+i);
			guardKindComboBox[i] = new JComboBox();
			for (String kind : kindSet) {
				guardKindComboBox[i].addItem(kind);
			}
			guardKindComboBox[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getOKButton().setEnabled(false);
				}
			});
			guardSettingButton[i] = new JButton(new GuardSettingButtonAction("Setting", i));
		}
	}

	abstract protected int getNumChildGuard();
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
		panel.addComponent(0, 0, 2, 1, new JLabel("Argument Guards"));
		for(int i = 0;i < getNumChildGuard();i++) {
			panel.addComponent(0, i+1, 0,  0, 2, 1, new JLabel("Guard Kind"));
			panel.addComponent(2, i+1, 10, 0, 7, 1, guardKindComboBox[i]);
			panel.addComponent(9, i+1, 0, 0, 1, 1,guardSettingButton[i]);
		}
		getOKButton().setEnabled(false);
		
		pack();
	}

}
