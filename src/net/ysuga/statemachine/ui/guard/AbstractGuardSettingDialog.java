package net.ysuga.statemachine.ui.guard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.ui.shape.GridLayoutPanel;
import net.ysuga.statemachine.ui.shape.TransitionSettingDialog;

import org.xml.sax.SAXException;

public abstract class AbstractGuardSettingDialog extends JDialog {

	static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

	public static final int OK_OPTION = JOptionPane.OK_OPTION;

	int exitOption = CANCEL_OPTION;

	static int okCount = 0;

	JPanel contentPane;

	JPanel parentContentPane;

	private TransitionSettingDialog transitionSettingDialog;
	
	public TransitionSettingDialog getTransitionSettingDialog() {
		return transitionSettingDialog;
	}
	
	private JButton okButton;

	public JButton getOKButton() {
		return okButton;
	}
	JTextField guardNameField;
	
	public String getGuardName() {
		return guardNameField.getText();
	}
	
	private AbstractGuardSettingDialog(TransitionSettingDialog transitionSettingDialog) {
		super();
		this.transitionSettingDialog = transitionSettingDialog;

		parentContentPane = (JPanel) this.getContentPane();
		guardNameField = new JTextField("Guard");	
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
		okButton = new JButton(new AbstractAction("OK") {
			public void actionPerformed(ActionEvent arg0) {
				onOk(arg0);
			}
		});

		contentPane = (JPanel)getContentPane();
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(1, 2));
		namePanel.add(new JLabel("Guard Name"));
		namePanel.add(guardNameField);
		contentPane.add(namePanel, BorderLayout.NORTH);
		GridLayoutPanel panel = new GridLayoutPanel();
		initPanel(panel);
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(okButton, BorderLayout.SOUTH);
		okButton.setRequestFocusEnabled(true);
		
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
		pack();
		setModal(true);
		setVisible(true);
		return exitOption;
	}

	public abstract Guard createGuard() throws InvalidGuardException;

	/**
	 * setDefaultSetting
	 * <div lang="ja">
	 * 
	 * @param guard
	 * </div>
	 * <div lang="en">
	 *
	 * @param guard
	 * </div>
	 */
	public abstract void setDefaultSetting(Guard guard);

	/**
	 * setGuardName
	 * <div lang="ja">
	 * 
	 * @param name
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * </div>
	 */
	public void setGuardName(String name) {
		this.guardNameField.setText(name);
	}
	

}
