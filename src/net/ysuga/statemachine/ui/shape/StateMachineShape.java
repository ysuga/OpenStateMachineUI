/**
 * StateMachineShape.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/08
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.state.StateCondition;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.shape.state.StateShape;
import net.ysuga.statemachine.ui.shape.state.StateShapeList;

/**
 * @author ysuga
 *
 */
public class StateMachineShape {

	final public ModelShape getSelectedShape() {
		for(ModelShape shape : stateShapeList) {
			if(shape.isSelected()) return shape;
		} 
		return null;
	}
	
	public StateShapeList stateShapeList;
	
	final public StateShape getStateShape(State state) {
		for(StateShape shape : stateShapeList) {
			if(state.equals(shape.getState())) {
				return (StateShape)shape;
			}
		}
		return null;
	}
	
	public TransitionShapeList transitionShapeList;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public StateMachineShape() {
		stateShapeList = new StateShapeList();
		transitionShapeList = new TransitionShapeList();
	}

	
	public void draw(Graphics g) {
		for(ModelShape stateShape : stateShapeList) {
			Color oldColor = g.getColor();
			if(stateShape.getOwnerState().getStateCondition().equals(StateCondition.ACTIVE)) {
				g.setColor(Color.red);
			}
			
			stateShape.draw((Graphics2D)g);
			g.setColor(oldColor);
		}
		
		for(TransitionShape transitionShape : transitionShapeList) {
			transitionShape.draw((Graphics2D)g);
		}
		
		ModelShape stateShape = getSelectedShape();
		if(stateShape != null) {
			Color oldColor = g.getColor();
			if(stateShape.getOwnerState().getStateCondition().equals(StateCondition.ACTIVE)) {
				g.setColor(Color.red);
			}
			
			stateShape.draw((Graphics2D)g);
			
			g.setColor(oldColor);
		}
		
		
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
	public StateShapeList getStateShapeList() {
		return stateShapeList;
	}


	/**
	 * <div lang="ja">
	 *
	 * @param selectedState
	 * </div>
	 * <div lang="en">
	 *
	 * @param selectedState
	 * </div>
	 */
	public void setSelectedState(State selectedState) {
		for(StateShape stateShape : stateShapeList) {
			if(stateShape.getState().equals(selectedState)) {
				stateShape.setSelected(true);
			}
		}
	}


	/**
	 * <div lang="ja">
	 *
	 * @param selectedState
	 * </div>
	 * <div lang="en">
	 *
	 * @param selectedState
	 * </div>
	 */
	public void setSlectedTransition(State selectedState) {
		for(StateShape stateShape : stateShapeList) {
			if(stateShape.getState() == selectedState) {
				stateShape.setSelected(true);
			}
		}
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
	public TransitionShapeList getTransitionShapeList() {
		return transitionShapeList;
	}


	/**
	 * <div lang="ja">
	 *
	 * @param selectedTransition
	 * </div>
	 * <div lang="en">
	 *
	 * @param selectedTransition
	 * </div>
	 */
	public void setSelectedTransition(Transition selectedTransition) {
		for(TransitionShape transitionShape : transitionShapeList) {
			if(transitionShape.getTransition().equals(selectedTransition)) {
				transitionShape.setSelected(true);
			}
		}
	}


}
