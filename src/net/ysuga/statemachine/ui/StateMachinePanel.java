/**
 * StateMachinePanel.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/07
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.shape.StateMachineShape;
import net.ysuga.statemachine.ui.shape.StateMachineShapeBuilder;
import net.ysuga.statemachine.ui.shape.TransitionPopupMenu;
import net.ysuga.statemachine.ui.state.DefaultStateSettingDialogFactory;
import net.ysuga.statemachine.ui.state.StatePopupMenu;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactoryManager;

/**
 * @author ysuga
 *
 */
public class StateMachinePanel extends JPanel {
	
	private StateMachine stateMachine;
	
	/**
	 * 
	 * <div lang="ja">
	 * StateMachineÇÃéÊìæ
	 * @return PanelÇ™ï€éùÇ∑ÇÈStateMachine
	 * </div>
	 * <div lang="en">
	 * 
	 * @return
	 * </div>
	 */
	public StateMachine getStateMachine() {
		return stateMachine;
	}
	
	
	private StateMachineShape stateMachineShape;
	
	public StateMachineShape getStateMachineShape() {
		return stateMachineShape;
	}
	

	private State selectedState;
	
	public State getSelectedState() {
		return selectedState;
	}
	
	public void setSelectedState(State state) {
		selectedTransition = null;
		selectedState = state;
	}
	
	private Transition selectedTransition;
	
	public Transition getSelectedTransition() {
		return selectedTransition;
	}
	
	private Point selectedPivot;
	
	public void setSelectedPivot(Point p) {
		selectedPivot = p;
	}
	
	public Point getSelectedPivot() {
		return selectedPivot;
	}
	/**
	 * <div lang="ja">
	 *
	 * @param transition
	 * </div>
	 * <div lang="en">
	 *
	 * @param transition
	 * </div>
	 */
	public void setSelectedTransition(Transition transition) {
		selectedTransition = transition;
		selectedState = null;
	}
	
	private StatePopupMenu statePopupMenu;
	
	public StatePopupMenu getStatePopupMenu() {
		return statePopupMenu;
	}
	
	private StateMachinePanelPopupMenu stateMachinePanelPopupMenu;
	private TransitionPopupMenu transitionPopupMenu;
	
	public StateMachinePanel(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
		this.stateMachineShape = StateMachineShapeBuilder.buildStateMachineShape(stateMachine);
		
		StateMachinePanelMouseAdapter adapter = new StateMachinePanelMouseAdapter(this);
		super.addMouseListener(adapter);
		super.addMouseMotionListener(adapter);
		
		StateSettingDialogFactoryManager.add(new DefaultStateSettingDialogFactory());

		this.statePopupMenu = new StatePopupMenu(this);
		this.transitionPopupMenu = new TransitionPopupMenu(this);
		this.stateMachinePanelPopupMenu = new StateMachinePanelPopupMenu(this);
	}
	
	/**
	 * 
	 * <div lang="ja">
	 * @param g
	 * </div>
	 * <div lang="en">
	 * @param g
	 * </div>
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = this.getSize();
		Rectangle2D.Double r = new Rectangle2D.Double(0, 0, d.width, d.height);
		g2d.setColor(Color.white);
		g2d.fill(r);
		g2d.setColor(Color.black);
		
		this.stateMachineShape = StateMachineShapeBuilder.buildStateMachineShape(stateMachine);
		stateMachineShape.setSelectedState(selectedState);
		stateMachineShape.setSelectedTransition(selectedTransition);
		stateMachineShape.draw(g);
	}

	/**
	 * <div lang="ja">
	 *
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public StateMachinePanelPopupMenu getPopupMenu() {
		return stateMachinePanelPopupMenu;
	}

	/**
	 * <div lang="ja">
	 *
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public TransitionPopupMenu getTransitionPopupMenu() {
		return transitionPopupMenu;
	}


}
