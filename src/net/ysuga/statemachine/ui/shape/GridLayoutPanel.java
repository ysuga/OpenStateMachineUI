package net.ysuga.statemachine.ui.shape;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class GridLayoutPanel extends JPanel {

	/**
	 * 
	 */
	public GridLayoutPanel() {
		super();
		gridBag = new GridBagLayout();
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		setLayout(gridBag);
	}
	
	protected GridBagLayout gridBag;
	protected GridBagConstraints constraints;
	

	final public void addComponent(int x, int y, int w, int h, Component label) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		gridBag.setConstraints(label, constraints);
		add(label);
	}
	

}
