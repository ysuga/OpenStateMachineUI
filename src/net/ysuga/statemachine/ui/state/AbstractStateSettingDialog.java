package net.ysuga.statemachine.ui.state;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;

public abstract class AbstractStateSettingDialog extends JDialog {

	public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

	public static final int OK_OPTION = JOptionPane.OK_OPTION;

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
	public AbstractStateSettingDialog(State state) {
		super();
		setTitle("StateSettingDialog");

		this.state = state;
		
		String stateName = "";
		if(state != null) {
			stateName = state.getName();
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

		initPanel();

		setSize(450, 250);

		pack();
	}

	protected void initParameterPanel() {}

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
		contentPane = new GridLayoutPanel();
		setContentPane(contentPane);

		contentPane.addComponent(0, 0, 8, 2, new JLabel(
				"Input State Machine Condition"));
		contentPane.addComponent(0, 2, 2, 1, new JLabel("State Name"));
		contentPane.addComponent(GridBagConstraints.RELATIVE, 2, GridBagConstraints.REMAINDER, 1, stateNameField);

		int line = 0;
		if (!this.stateNameField.getText().equals("start")
				&& !this.stateNameField.getText().equals("exit")) {
			baseOffset = 3;
			initParameterPanel();
		}

		contentPane.addComponent(10, 3+maxLine, 3, 1, new JButton(
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
		contentPane.addComponent(10, 4+maxLine, 3, 1, okButton);
		okButton.setRequestFocusEnabled(true);
	}

	public int doModal() {
		setModal(true);
		setVisible(true);
		return exitOption;
	}
	
	public void onOk() {
		exitOption = OK_OPTION;
		setVisible(false);
	}
	
	
	abstract public State createState();
	
}
