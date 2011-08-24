package net.ysuga.statemachine.ui.shape;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.guard.GuardFactoryManager;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.StateMachinePanel;
import net.ysuga.statemachine.ui.guard.AbstractGuardSettingDialog;
import net.ysuga.statemachine.ui.guard.GuardSettingDialogFactory;
import net.ysuga.statemachine.ui.guard.GuardSettingDialogFactoryManager;

import org.xml.sax.SAXException;

public class TransitionSettingDialog extends JDialog {

	static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

	static final int OK_OPTION = JOptionPane.OK_OPTION;

	int exitOption = CANCEL_OPTION;

	static int okCount = 0;

	private Transition transition;
	
	private State sourceState;

	JTextField transitionNameField;

	JComboBox fromNameComboBox;

	JComboBox toNameComboBox;

	// JTextField operatorNameField;

	// JComboBox operatorComboBox;

	private JComboBox guardKindComboBox;

	private JButton guardSettingButton;

	GridLayoutPanel contentPane;

	JPanel parentContentPane;

	private Guard guard;
	
	private StateMachinePanel panel;

	public StateMachinePanel getStateMachinePanel() {return panel;}

	private HashMap<String, TreeMap<String, JComponent>> operatorAndOperandComboBoxesMap;

	/**
	 * 
	 * <div lang="ja">
	 * コンストラクタ
	 * @param panel
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param panel
	 * </div>
	 */
	public TransitionSettingDialog(StateMachinePanel panel) {
		super();
		this.panel = panel;

		parentContentPane = (JPanel) this.getContentPane();
		transitionNameField = new JTextField(20);
		transitionNameField.setText("transition" + okCount);

		toNameComboBox = new JComboBox();
		fromNameComboBox = new JComboBox();

		for (State state : panel.getStateMachine().getStateCollection()) {
			String stateName = state.getName();
			if (!stateName.equals(StateMachineTagNames.START)) {
				toNameComboBox.addItem(stateName);
			}

			if (!stateName.equals(StateMachineTagNames.EXIT)) {
				fromNameComboBox.addItem(stateName);
			}
		}


		okButton = new JButton(new AbstractAction("OK") {
			public void actionPerformed(ActionEvent arg0) {
				onOk(arg0);
			}
		});
		
		guardKindComboBox = new JComboBox();
		Set<String> kindSet = GuardFactoryManager.getInstance().getKindSet();
		for (String kind : kindSet) {
			guardKindComboBox.addItem(kind);
		}
		
		guardKindComboBox.addActionListener(new ActionListener() {
			private boolean initFlag;
			public void actionPerformed(ActionEvent e) {
				okButton.setEnabled(false);
				if(!initFlag) {
					initFlag = true;
				} else {
					transition = null;
				}
			}
		});

		guardSettingButton = new JButton(new AbstractAction("Setting") {
			public void actionPerformed(ActionEvent e) {
				onGuardSettingButtonPressed();
			}
		});
	}

	private void onGuardSettingButtonPressed() {
		String kind = (String) guardKindComboBox.getSelectedItem();
		GuardSettingDialogFactory factory = GuardSettingDialogFactoryManager
				.getInstance()
				.get(kind);
		if (factory == null) {
			JOptionPane.showMessageDialog(panel, "Guard Kind(" + guardKindComboBox.getSelectedItem() + ") is not properly registered");
		}
		AbstractGuardSettingDialog guardSettingDialog = factory.createGuardSettingDialog(this);
		if(transition != null) {
			try {
				guardSettingDialog.setGuardName(transition.getGuard().getName());
				guardSettingDialog.setDefaultSetting(transition.getGuard());
			} catch (ClassCastException e) {
				
			}
		}
		if(guardSettingDialog.doModal() == AbstractGuardSettingDialog.OK_OPTION) {
			try {
				guard = guardSettingDialog.createGuard();
			} catch (InvalidGuardException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return;
			}
			okButton.setEnabled(true);
		}
	}

	/**
	 * @param loader
	 * @param arg0
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public TransitionSettingDialog(StateMachinePanel panel,
			Transition transition) {
		this(panel);
		this.transition = transition;
		this.sourceState = transition.getSourceState();
		// this.targetState = transition.getTargetState();
		transitionNameField.setText(transition.getName());

		fromNameComboBox.setSelectedItem(transition.getSourceState().getName());
		toNameComboBox.setSelectedItem(transition.getTargetState().getName());

		guardKindComboBox.setSelectedItem(transition.getGuard().getKind());

	}

	int maxLine = 3;
	
	private JButton okButton;

	/**
	 * 
	 * 
	 * @return void
	 */
	private void initPanel() {
		contentPane = new GridLayoutPanel();
		setContentPane(contentPane);

		contentPane.addComponent(0, 0, 10, 10, 10, 2, new JLabel(
				"Input Transition Setting"));
		contentPane.addComponent(0, 2, 10, 0, 2, 1, new JLabel(
				"Transition Name"));
		contentPane.addComponent(GridBagConstraints.RELATIVE, 2, 100, 0, 8, 1,
				transitionNameField);

		contentPane.addComponent(0, 3, 10, 0, 2, 1, new JLabel("Source State"));
		contentPane.addComponent(GridBagConstraints.RELATIVE, 3, 8, 1,
				fromNameComboBox);

		contentPane.addComponent(0, 4, 10, 0, 2, 1, new JLabel("Target State"));
		contentPane.addComponent(GridBagConstraints.RELATIVE, 4, 8, 1,
				toNameComboBox);

		contentPane.addComponent(0, 5, 10, 0, 2, 1, new JLabel("Guard Kind"));
		contentPane.addComponent(GridBagConstraints.RELATIVE, 5, 10, 0, 7, 1,
				guardKindComboBox);
		contentPane.addComponent(GridBagConstraints.RELATIVE, 5, 0, 0, 1, 1,
				guardSettingButton);

		int line = 0;
		// baseOffset = 3;
		// initParameterPanel();

		contentPane.addComponent(9, 6 + maxLine, 0, 0, 1, 1, new JButton(
				new AbstractAction("Cancel") {
					public void actionPerformed(ActionEvent arg0) {
						exitOption = CANCEL_OPTION;
						setVisible(false);
					}
				}));

		okButton.setEnabled(false);
		contentPane.addComponent(9, 7 + maxLine, 0, 0, 1, 1, okButton);
		okButton.setRequestFocusEnabled(true);
		pack();
	}

	final private void canceled(ActionEvent arg0) {
		exitOption = CANCEL_OPTION;
		setVisible(false);
	}

	final private void onOk(ActionEvent arg0) {
		exitOption = OK_OPTION;
		okCount++;
		setVisible(false);
	}

	public int doModal() {
		initPanel();
		exitOption = CANCEL_OPTION;
		//setSize(400, 400);
		pack();
		setModal(true);
		setVisible(true);
		return exitOption;
	}


	public void createTransition() throws InvalidConnectionException {
		// String transitionName = transitionNameField.getText();
		StateMachine stateMachine = panel.getStateMachine();
		String newTransitionName = this.transitionNameField.getText();
		State newSourceState = stateMachine
				.getState((String) this.fromNameComboBox.getSelectedItem());
		State newTargetState = stateMachine
				.getState((String) this.toNameComboBox.getSelectedItem());
		Guard newGuard = guard;
		if (newSourceState == null || newTargetState == null) {
			// TODO : error
		}

		newSourceState.connect(newTransitionName, newTargetState, newGuard);
	}

	/**
	 * setSourceStateName
	 * <div lang="ja">
	 * 
	 * @param name
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * </div>
	 */
	public void setSourceStateName(String name) {
		this.fromNameComboBox.setSelectedItem(name);
	}
}
