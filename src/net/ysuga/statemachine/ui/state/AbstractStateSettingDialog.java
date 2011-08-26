package net.ysuga.statemachine.ui.state;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.ExitState;
import net.ysuga.statemachine.state.StartState;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.state.action.StateActionList;
import net.ysuga.statemachine.ui.StateMachinePanel;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;

public abstract class AbstractStateSettingDialog extends JDialog {

	public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

	public static final int OK_OPTION = JOptionPane.OK_OPTION;

	static int okCount;
	
	private int exitOption = CANCEL_OPTION;

	private State state;
	public State getState() {
		return state;
	}
	
	private JTextField stateNameField;

	final public String getStateName() {
		return stateNameField.getText();
	}

	private GridLayoutPanel contentPane;

	private StateActionList onEntryStateActionList;
	
	final public StateActionList getOnEntryStateActionList() {
		return onEntryStateActionList;
	}

	
	private StateActionList onOperateStateActionList;

	final public StateActionList getOnExitStateActionList() {
		return onExitStateActionList;
	}

	
	private StateActionList onExitStateActionList;

	final public StateActionList getOnOperateStateActionList() {
		return onOperateStateActionList;
	}

	
	private JButton onEntrySettingButton;
	private JButton onOperateSettingButton;
	private JButton onExitSettingButton;
	
	/**
	 * 
	 * <div lang="ja">
	 * コンストラクタ
	 * @param panel
	 * @param stateName
	 * @param fileName
	 * @param state
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param panel
	 * @param stateName
	 * @param fileName
	 * @param state
	 * </div>
	 */
	public AbstractStateSettingDialog(StateMachinePanel panel, State state) {
		//super();
		setTitle("StateSettingDialog");

		this.state = state;
		
		String stateName = "";
		if(state != null) {
			stateName = state.getName();
			onEntryStateActionList = state.getOnEntryActionList();
			onOperateStateActionList = state.getOnOperateActionList();
			onExitStateActionList = state.getOnExitActionList();
		}  else {
			int counter = 0;
			StateMachine stateMachine = panel.getStateMachine();
			while(true) {
				stateName = "state" + counter;
				if(stateMachine.getState(stateName) == null) {
					break;
				}
				counter++;
			}
			onEntryStateActionList = new StateActionList();
			onOperateStateActionList = new StateActionList();
			onExitStateActionList = new StateActionList();
		}
		
		stateNameField = new JTextField(20);
		stateNameField.setText(stateName);
		stateNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOk();
			}
		});

		if (stateName.equals("start") || stateName.equals("exit")) {
			stateNameField.setEditable(false);
		}

		onOperateSettingButton = new JButton(new AbstractAction("Setting") {
			public void actionPerformed(ActionEvent e) {
				onOperateSetting();
			}
		});
		onEntrySettingButton = new JButton(new AbstractAction("Setting") {
			public void actionPerformed(ActionEvent e) {
				onEntrySetting();
			}
		});
		onExitSettingButton = new JButton(new AbstractAction("Setting") {
			public void actionPerformed(ActionEvent e) {
				onExitSetting();
			}
		});
	}

	private void onEntrySetting() {
		StateActionSettingDialog dialog = new StateActionSettingDialog(onEntryStateActionList);
		if(dialog.doModal() == OK_OPTION) {
			this.onEntryStateActionList = dialog.getStateActionList();
		}
	}
	
	private void onOperateSetting() {
		StateActionSettingDialog dialog = new StateActionSettingDialog(onOperateStateActionList);
		if(dialog.doModal() == OK_OPTION) {
			this.onOperateStateActionList = dialog.getStateActionList();			
		}
	}
	
	private void onExitSetting() {
		StateActionSettingDialog dialog = new StateActionSettingDialog(onExitStateActionList);
		if(dialog.doModal() == OK_OPTION) {
			this.onExitStateActionList = dialog.getStateActionList();			
		}
	}
	
	
	protected void initParameterPanel(GridLayoutPanel parameterPanel) {}

	int baseOffset;
	int maxLine;
	
	protected void addComponent(int x, int y, int w, int h, Component component) {
		contentPane.addComponent(x, y+baseOffset, w, h, component);
		if(maxLine <y+baseOffset) {
			maxLine = y+baseOffset;
		}
	}
			
	/**
	 * 
	 * 
	 * @return void
	 */
	private void initPanel() {
		setContentPane(new JPanel());
		getContentPane().setLayout(new GridLayout(3, 1));
		GridLayoutPanel basicInputPanel = new GridLayoutPanel();
		basicInputPanel.addComponent(0, 0, 8, 2, new JLabel(
				"Input State Machine Condition"));
		basicInputPanel.addComponent(0, 2, 2, 1, new JLabel("State Name"));
		basicInputPanel.addComponent(GridBagConstraints.RELATIVE, 2, GridBagConstraints.REMAINDER, 1, stateNameField);
		getContentPane().add(basicInputPanel);
		
		// Setting Default State Name
		
		basicInputPanel.addComponent(0, 3, 7, 1, new JLabel("OnEntry"));
		basicInputPanel.addComponent(4, 3, GridBagConstraints.REMAINDER, 1, onEntrySettingButton);

		basicInputPanel.addComponent(0, 4, 7, 1, new JLabel("OnOperate"));
		basicInputPanel.addComponent(4, 4, GridBagConstraints.REMAINDER, 1, onOperateSettingButton);

		basicInputPanel.addComponent(0, 5, 7, 1, new JLabel("OnExit"));
		basicInputPanel.addComponent(4, 5, GridBagConstraints.REMAINDER, 1, onExitSettingButton);
		
//		contentPane = new GridLayoutPanel();
//		setContentPane(contentPane);


		GridLayoutPanel parameterPanel = new GridLayoutPanel();
		
		int line = 0;
		if (!this.stateNameField.getText().equals("start")
				&& !this.stateNameField.getText().equals("exit")) {
			baseOffset = 3;
			initParameterPanel(parameterPanel);
		}
		getContentPane().add(parameterPanel);
		
		GridLayoutPanel bottomPanel = new GridLayoutPanel();
		
		bottomPanel.addComponent(10, 0, 3, 1, new JButton(
				new AbstractAction("Cancel") {
					public void actionPerformed(ActionEvent arg0) {
						exitOption = CANCEL_OPTION;
						setVisible(false);
					}
				}));
		
		JButton okButton = new JButton(new AbstractAction("OK") {
			public void actionPerformed(ActionEvent arg0) {
				onOk();
			}
		});
		bottomPanel.addComponent(10, 1, 3, 1, okButton);
		okButton.setRequestFocusEnabled(true);
		
		getContentPane().add(bottomPanel);
	}

	public int doModal() {
		initPanel();

		setSize(450, 250);

		pack();
		
		setModal(true);
		setVisible(true);
		return exitOption;
	}
	
	public void onOk() {
		exitOption = OK_OPTION;
		okCount++;
		setVisible(false);
	}
	
	
	abstract protected State createState();
	
	public State buildState() {
		if(stateNameField.getText().equals(StateMachineTagNames.START)){
			return new StartState();
		} else if(stateNameField.getText().equals(StateMachineTagNames.EXIT)){
			return new ExitState();
		}
		State state = createState();
		state.getOnEntryActionList().clear();
		state.getOnOperateActionList().clear();
		state.getOnExitActionList().clear();
		state.getOnEntryActionList().addAll(getOnEntryStateActionList());
		state.getOnOperateActionList().addAll(getOnOperateStateActionList());
		state.getOnExitActionList().addAll(getOnExitStateActionList());
		return state;
	}

	/**
	 * setStateNameField
	 * <div lang="ja">
	 * 
	 * @param string
	 * </div>
	 * <div lang="en">
	 *
	 * @param string
	 * </div>
	 */
	public void setStateNameField(String string) {
		this.stateNameField.setText(string);
	}
}
