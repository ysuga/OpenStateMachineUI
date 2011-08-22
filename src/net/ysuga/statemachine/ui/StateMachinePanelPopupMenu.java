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
import javax.swing.JSeparator;

import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.state.AbstractStateSettingDialog;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactory;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactoryManager;


/**
 * 
 * <div lang="ja">
 * StateMachinePanel�N���X�p�̃|�b�v�A�b�v���j���[����т��̏����D
 * 
 * �|�b�v�A�b�v�֘A�̏����͂ł��邾�������ɋL�q����悤�ɂ��Ă��܂��D
 * </div>
 * <div lang="en">
 *
 * </div>
 * @author ysuga
 *
 */
public class StateMachinePanelPopupMenu {

	/**
	 * �|�b�v�A�b�v���j���[�{��
	 */
	private JPopupMenu popupMenu;
	
	/**
	 * �p�l���{��
	 */
	private StateMachinePanel panel;
	
	/**
	 * ���j���[�̈ʒu��ۑ����Ă����o�b�t�@
	 */
	private Point location;
	
	/**
	 * 
	 * <div lang="ja">
	 * ���j���[�̈ʒu�̎擾
	 * @return ���j���[�̕\�����ꂽ�ʒu
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * �V�K�X�e�[�g�ǉ��̂��߂̃A�N�V�����N���X
	 * @author ysuga
	 *
	 */
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
	
	private JMenuItem startMenuItem; 
	private JMenuItem suspendMenuItem; 
	private JMenuItem resumeMenuItem; 
	private JMenuItem stopMenuItem; 
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	/**
	 * <div lang="ja">
	 * �R���X�g���N�^
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
		startMenuItem = new JMenuItem(new AbstractAction("Start") {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		suspendMenuItem = new JMenuItem(new AbstractAction("Suspend") {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		suspendMenuItem.setEnabled(false);
		
		resumeMenuItem = new JMenuItem(new AbstractAction("Resume") {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		resumeMenuItem.setEnabled(false);
		
		stopMenuItem = new JMenuItem(new AbstractAction("Stop") {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		stopMenuItem.setEnabled(false);
		
		popupMenu.add(startMenuItem);
		popupMenu.add(suspendMenuItem);
		popupMenu.add(resumeMenuItem);
		popupMenu.add(stopMenuItem);

		popupMenu.add(new JSeparator());
		

		Set<String> kindSet = StateSettingDialogFactoryManager.getInstance().getKindList();
		for(String kind : kindSet) {
			JMenuItem addNewMenuItem = new JMenuItem(
					new AddNewStateAction("Add New State(" + kind + ")", kind));
			popupMenu.add(addNewMenuItem);
		}

		popupMenu.add(new JSeparator());
		
		newMenuItem = new JMenuItem(new AbstractAction("New") {
			public void actionPerformed(ActionEvent e) {
				onNew();
			}
		});

		openMenuItem = new JMenuItem(new AbstractAction("Open") {
			public void actionPerformed(ActionEvent e) {
				onOpen();
			}
		});
		saveMenuItem = new JMenuItem(new AbstractAction("Save"){
			public void actionPerformed(ActionEvent e) {
				onSaveAs();
			}
		});
		saveAsMenuItem = new JMenuItem(new AbstractAction("Save As..."){
			public void actionPerformed(ActionEvent e) {
				onSaveAs();
			}
		});
		popupMenu.add(newMenuItem);
		popupMenu.add(openMenuItem);
		popupMenu.add(saveMenuItem);
		popupMenu.add(saveAsMenuItem);
				
		location = point;
		popupMenu.show(component, point.x, point.y);
	}

	/**
	 * 
	 * onNew
	 * <div lang="ja">
	 * 
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void onNew() {
		panel.createNewStateMachine();
	}
	
	/**
	 * 
	 * open
	 * <div lang="ja">
	 * �t�@�C���I���_�C�A���O��\�������āCStateMachine���J���܂��D
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void onOpen() {
		panel.showOpenFileDialog();
	}
	
	public void onSaveAs() {
		panel.showSaveFileDialog();
	}

}
