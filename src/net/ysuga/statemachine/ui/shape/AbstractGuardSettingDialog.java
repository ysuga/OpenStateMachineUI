package net.ysuga.statemachine.ui.shape;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import net.ysuga.statemachine.guard.DelayGuard;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.guard.GuardFactoryManager;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.StateMachinePanel;

import org.xml.sax.SAXException;

public abstract class AbstractGuardSettingDialog extends JDialog {

	static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

	static final int OK_OPTION = JOptionPane.OK_OPTION;

	int exitOption = CANCEL_OPTION;

	static int okCount = 0;

	JPanel contentPane;

	JPanel parentContentPane;

	private TransitionSettingDialog transitionSettingDialog;
	
	private AbstractGuardSettingDialog(TransitionSettingDialog transitionSettingDialog) {
		super();
		this.transitionSettingDialog = transitionSettingDialog;

		parentContentPane = (JPanel) this.getContentPane();
		
	}
	/**
	 * @param loader
	 * @param arg0
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public AbstractGuardSettingDialog(TransitionSettingDialog transitionSettingDialog, Guard guard)
	{
		this(transitionSettingDialog);
	}
	
	int maxLine = 3;
	
	abstract protected void initPanel(GridLayoutPanel panel);
	
	/**
	 * 
	 * 
	 * @return void
	 */
	private void initPanel() {
		contentPane = (JPanel)getContentPane();
		contentPane.add(new JLabel("Input Guard Parameter"), BorderLayout.NORTH);
		GridLayoutPanel panel = new GridLayoutPanel();
		initPanel(panel);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JButton okButton = new JButton(new AbstractAction("OK") {
			public void actionPerformed(ActionEvent arg0) {
				onOk(arg0);
			}
		});
		contentPane.add(okButton, BorderLayout.SOUTH);
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
		setSize(400, 400);
		setModal(true);
		setVisible(true);
		return exitOption;
	}

	public Guard createGuard() {
		return new DelayGuard("test1", 500);
	}
	

}
