/**
 * StateMachineShapeBuilder.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/08
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import java.util.Iterator;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.shape.state.DefaultStateShape;
import net.ysuga.statemachine.ui.shape.state.ExitStateShape;
import net.ysuga.statemachine.ui.shape.state.StartStateShape;
import net.ysuga.statemachine.ui.shape.state.StateShape;

/**
 * @author ysuga
 *
 */
public class StateMachineShapeBuilder {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public StateMachineShapeBuilder() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	static public StateMachineShape buildStateMachineShape(StateMachine stateMachine) {
		StateMachineShape stateMachineShape = new StateMachineShape();
		synchronized(stateMachine) {
			for(State state : stateMachine.getStateCollection()) {
				if(state.getName().equals(StateMachineTagNames.START)) {
					stateMachineShape.stateShapeList.add(new StartStateShape(state));
 				} else if(state.getName().equals(StateMachineTagNames.EXIT)) {
					stateMachineShape.stateShapeList.add(new ExitStateShape(state));
 				} else {
 					stateMachineShape.stateShapeList.add(new DefaultStateShape(state));
 				}
			}
			
			for(State state : stateMachine.getStateCollection()) {
				Iterator<Transition> i = state.getTransitionIterator();
				while(i.hasNext()) {
					Transition transition = i.next();
//				}
//				for(Transition transition : state.getTransitionMap().values()) {
					StateShape sourceShape = stateMachineShape.getStateShape(transition.getSourceState());
					
					StateShape targetShape = stateMachineShape.getStateShape(transition.getTargetState());
					stateMachineShape.transitionShapeList.add(new TransitionShape(transition, sourceShape, targetShape));
				}
			}

		}
		stateMachineShape.setStateMachine(stateMachine);
		return stateMachineShape;
	}

}
