/**
 * StateMachineShapeBuilder.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/08
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.shape;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.transition.Transition;

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
			for(State state : stateMachine.getStateMap().values()) {
				if(state.getName() == StateMachineTagNames.START) {
					stateMachineShape.stateShapeList.add(new StartStateShape(state));
 				}else {
 					stateMachineShape.stateShapeList.add(new DefaultStateShape(state));
 				}
			}
			
			for(State state : stateMachine.getStateMap().values()) {
				for(Transition transition : state.getTransitionMap().values()) {
					StateShape sourceShape = stateMachineShape.getStateShape(transition.getSourceState());
					StateShape targetShape = stateMachineShape.getStateShape(transition.getTargetState());
					stateMachineShape.transitionShapeList.add(new TransitionShape(transition, sourceShape, targetShape));
				}
			}

		}
		return stateMachineShape;
	}

}
