import java.awt.Point;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.guard.DelayGuard;
import net.ysuga.statemachine.state.DefaultState;
import net.ysuga.statemachine.state.StartState;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.ui.StateMachinePanel;

/**
 * StateMachinePanelTest.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/07
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */

/**
 * @author ysuga
 * 
 */
public class StateMachinePanelTest {

	public StateMachinePanelTest() {
		new MainFrame();
	}

	public class MainFrame extends JFrame {
		public MainFrame() {
			super("StateMachinePanelTest");
			setSize(500, 500);
			setVisible(true);

			StateMachine stateMachine;
			try {
				stateMachine = new StateMachine("StateMachinePanelTest");
				
				State state1 = new DefaultState("state1");
				state1.setLocation(new Point(100, 100));
				State start = new StartState();
				State state2 = new DefaultState("state2");
				stateMachine.add(start);
				stateMachine.add(state1);
				stateMachine.add(state2);
				
				try {
					start.connect("startConnection", state1, new DelayGuard("delay1", 500));
				} catch (InvalidConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				StateMachinePanel stateMachinePanel = new StateMachinePanel(
						stateMachine);
				getContentPane().add(stateMachinePanel);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (InvalidStateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

	static public void main(String[] args) {
		new StateMachinePanelTest();
	}
}
