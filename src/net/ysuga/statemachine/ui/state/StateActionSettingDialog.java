/**
 * StateActionSettingDialog.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/23
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.state;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import net.ysuga.statemachine.state.action.StateAction;
import net.ysuga.statemachine.state.action.StateActionFactoryManager;
import net.ysuga.statemachine.state.action.StateActionList;
import net.ysuga.statemachine.ui.ParameterMapSettingDialog;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;
import net.ysuga.statemachine.util.ParameterMap;

/**
 * <div lang="ja">
 * 
 * </div> <div lang="en">
 * 
 * </div>
 * 
 * @author ysuga
 * 
 */
public class StateActionSettingDialog extends JDialog {

	public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

	public static final int OK_OPTION = JOptionPane.OK_OPTION;

	private StateActionList stateActionList;

	public StateActionList getStateActionList() {
		return stateActionList;
	}

	private int exitOption = CANCEL_OPTION;

	private JList actionList;
	

	public StateActionSettingDialog(StateActionList stateActionList) {
		super();
		setTitle("StateAction Setting Dialog");

		this.stateActionList = stateActionList;
	}

	public void initPanel() {
		GridLayoutPanel contentPane = new GridLayoutPanel();
		setContentPane(contentPane);

		contentPane.addComponent(0, 0, 10, 1, new JLabel(
				"Modify StateAction List"));
		contentPane.addComponent(0, 1, 1, 1, new JLabel("Action List"));

		actionList = new JList();
		DefaultListModel model = new DefaultListModel();
		for (StateAction stateAction : stateActionList) {
			model.addElement(stateAction.getKind());
		}
		actionList.setModel(model);
		actionList.setLayoutOrientation(JList.VERTICAL);
		actionList.setVisibleRowCount(0);
		// actionList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		actionList.setFixedCellWidth(60);
		actionList.setFixedCellHeight(20);

		JScrollPane scrollPane = new JScrollPane(actionList);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(200, 200));

		contentPane.addComponent(1, 1, 10, 0, 8, 5, scrollPane);
		contentPane.addComponent(10, 1, 0, 0, 1, 1, new JButton(
				new AbstractAction("OK") {
					public void actionPerformed(ActionEvent e) {
						onOk();
					}
				}));
		contentPane.addComponent(10, 2, 0, 0, 1, 1, new JButton(
				new AbstractAction("CANCEL") {
					public void actionPerformed(ActionEvent e) {
						onCancel();
					}
				}));

		contentPane.addComponent(9, 1, 0, 0, 1, 1, new JButton(
				new AbstractAction("ADD") {
					public void actionPerformed(ActionEvent e) {
						onAdd();
					}
				}));
		contentPane.addComponent(9, 2, 0, 0, 1, 1, new JButton(
				new AbstractAction("REMOVE") {
					public void actionPerformed(ActionEvent e) {
						onRemove();
					}
				}));
		contentPane.addComponent(9, 3, 0, 0, 1, 1, new JButton(
				new AbstractAction("UP") {
					public void actionPerformed(ActionEvent e) {
						onUp();
					}
				}));
		contentPane.addComponent(9, 4, 0, 0, 1, 1, new JButton(
				new AbstractAction("DOWN") {
					public void actionPerformed(ActionEvent e) {
						onDown();
					}
				}));
		contentPane.addComponent(9, 5, 0, 10, 1, 1, new JLabel("   "));
		pack();
	}

	public int doModal() {
		initPanel();
		setModal(true);
		setVisible(true);
		return exitOption;
	}

	public class StateActionAddingDialog extends JDialog {
		private int exitOption = CANCEL_OPTION;

		private JComboBox stateActionComboBox;

		private ParameterMap parameterMap;

		public StateActionAddingDialog() {
			super();

			GridLayoutPanel contentPane = new GridLayoutPanel();
			setContentPane(contentPane);
			contentPane.addComponent(0, 0, 10, 1, new JLabel(
					"Select StateAction"));
			stateActionComboBox = new JComboBox();
			stateActionComboBox.setPreferredSize(new Dimension(200, 20));

			// TODO:
			Set<String> keySet = StateActionFactoryManager.getInstance()
					.getKindSet();
			for (String kind : keySet) {
				stateActionComboBox.addItem(kind);
			}

			contentPane.addComponent(0, 1, 10, 10, 8, 1, stateActionComboBox);
			contentPane.addComponent(8, 1, 0, 0, 1, 1, new JButton(
					new AbstractAction("Setting") {
						public void actionPerformed(ActionEvent e) {
							/**JOptionPane.showMessageDialog(
									null,
									"Creating StateAction("
											+ (String) stateActionComboBox
													.getSelectedItem() + ")");**/
							StateAction sa = StateActionFactoryManager
									.getInstance()
									.get((String) stateActionComboBox
											.getSelectedItem())
									.createStateAction();
							ParameterMap pm = sa.getParameterMap();
							ParameterMapSettingDialog dialog = new ParameterMapSettingDialog(pm);
							if(dialog.doModal() == JOptionPane.OK_OPTION) {
								parameterMap = dialog.createParameterMap(); 
							}
						}
					}));
			contentPane.addComponent(9, 1, 0, 0, 1, 1, new JButton(
					new AbstractAction("OK") {
						public void actionPerformed(ActionEvent e) {
							exitOption = OK_OPTION;
							setVisible(false);
						}
					}));
			contentPane.addComponent(9, 2, 0, 0, 1, 1, new JButton(
					new AbstractAction("CANCEL") {
						public void actionPerformed(ActionEvent e) {
							setVisible(false);
						}
					}));
		}

		public int doModal() {
			setModal(true);
			pack();
			// setSize(400, getSize().height);
			setVisible(true);
			return exitOption;
		}

		public StateAction createStateAction() {
			StateAction sa = StateActionFactoryManager.getInstance()
					.get((String) stateActionComboBox.getSelectedItem())
					.createStateAction();
			if(parameterMap != null) {
				sa.setParameterMap(parameterMap);
			}
			return sa;
		}
	};

	/**
	 * 
	 * onOk <div lang="ja">
	 * 
	 * </div> <div lang="en">
	 * 
	 * </div>
	 */
	public void onOk() {
		exitOption = OK_OPTION;
		setVisible(false);
	}

	/**
	 * 
	 * onCancel <div lang="ja">
	 * 
	 * </div> <div lang="en">
	 * 
	 * </div>
	 */
	public void onCancel() {
		setVisible(false);
	}

	/**
	 * 
	 * onAdd <div lang="ja">
	 * 
	 * </div> <div lang="en">
	 * 
	 * </div>
	 */
	public void onAdd() {
		StateActionAddingDialog dialog = new StateActionAddingDialog();
		if (dialog.doModal() == OK_OPTION) {
			StateAction stateAction = dialog.createStateAction();
			this.stateActionList.add(stateAction);
			initPanel();
			repaint();
		}
	}

	public void onRemove() {
		int index = actionList.getSelectedIndex();
		if (index >= 0) {
			stateActionList.remove(index);
		}
		initPanel();
		repaint();
	}

	public void onUp() {
		int index = actionList.getSelectedIndex();
		if (index > 0) {
			StateAction action = stateActionList.remove(index);
			if (action != null) {
				stateActionList.add(index - 1, action);
				initPanel();
				actionList.setSelectedIndex(index - 1);
				repaint();
			}
		}
	}

	public void onDown() {
		int index = actionList.getSelectedIndex();

		if (index != stateActionList.size() - 1 && index >= 0) {
			StateAction action = stateActionList.remove(index);
			if (action != null) {
				stateActionList.add(index + 1, action);
				initPanel();
				actionList.setSelectedIndex(index + 1);
				repaint();
			}
		}
	}
}
