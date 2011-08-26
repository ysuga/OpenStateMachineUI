/**
 * ParameterMapSettingDialog.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/25
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.ysuga.statemachine.ui.shape.GridLayoutPanel;
import net.ysuga.statemachine.util.ParameterMap;

/**
 * <div lang="ja">
 *
 * </div>
 * <div lang="en">
 *
 * </div>
 * @author ysuga
 *
 */
public class ParameterMapSettingDialog extends JDialog {
	private int exitOption = JOptionPane.CANCEL_OPTION;
	
	private ParameterMap parameterMap;
	
	private JTextField[] textField;
	
	private JButton okButton;
	private JButton cancelButton;
	
	
	public ParameterMapSettingDialog(ParameterMap pm) {
		super();
		this.parameterMap = pm;
	}
	
	private void onOk() {
		exitOption = JOptionPane.OK_OPTION;
		setVisible(false);
	}
	
	private void onCancel() {
		setVisible(false);
	}
	
	public int doModal() {
		GridLayoutPanel glp = new GridLayoutPanel();
		setContentPane(glp);
		int line = 0;

		if(parameterMap != null) {
			textField = new JTextField[parameterMap.size()];
			for(String key : parameterMap.keySet()) {
				JLabel label = new JLabel(key);
				textField[line] = new JTextField(parameterMap.get(key));
				glp.addComponent(0, line, 0, 0, 1, 1, label);
				glp.addComponent(1, line, 10, 10, 8, 1, textField[line]);
				line++;
			}
		}
		
		if(parameterMap == null || parameterMap.size() == 0) {
			glp.addComponent(0, line, 10, 1, new JLabel("This StateAction has no setting item."));
			line++;
		}
		okButton = new JButton(new AbstractAction("OK") {
			public void actionPerformed(ActionEvent e) {
				onOk();
			}
		});
		glp.addComponent(8, line, 1, 1, okButton);
		cancelButton = new JButton(new AbstractAction("CANCEL") {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});
		glp.addComponent(8, line+1, 1, 1, cancelButton);
		setModal(true);
		pack();
		setVisible(true);
		return exitOption;
	}

	/**
	 * createParameterMap
	 * <div lang="ja">
	 * 
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public ParameterMap createParameterMap() {
		if(parameterMap == null) return null;
		
		int line = 0;
		for(String key : parameterMap.keySet()) {
			parameterMap.remove(key);
			parameterMap.put(key, textField[line].getText());
			line++;
		}
		
		return parameterMap;
	}
}
