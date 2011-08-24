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
import net.ysuga.statemachine.ui.state.DefaultStateSettingDialogFactory;
import net.ysuga.statemachine.ui.state.StateSettingDialogFactoryManager;

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
public class StateMachinePanelTest  implements Runnable{

	public StateMachinePanelTest() {

		StateSettingDialogFactoryManager
				.add(new DefaultStateSettingDialogFactory());
		
		new MainFrame();
		Thread thread = new Thread(this);
		thread.start();
	}

	StateMachinePanel stateMachinePanel;
	
	public class MainFrame extends JFrame {
		public MainFrame() {
			super("StateMachinePanelTest");

			StateMachine stateMachine;
			try {

				stateMachinePanel = new StateMachinePanel();
				
				stateMachine = stateMachinePanel.getStateMachine();
				State start = new StartState();
				start.setLocation(new Point(200, 100));
				State state1 = new DefaultState("state1");
				state1.setLocation(new Point(100, 200));
				State state2 = new DefaultState("state2");
				state2.setLocation(new Point(300, 200));
				stateMachine.add(start);
				stateMachine.add(state1);
				stateMachine.add(state2);
				
				try {
					start.connect("startConnection", state1, new DelayGuard("delay1", 5000));
					state1.connect("transition1", state2, new DelayGuard("delay2", 5000));
				} catch (InvalidConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				getContentPane().add(stateMachinePanel);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (InvalidStateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(500, 500);
			setVisible(true);

		}
	}
	
	public void run() {
		while(true) {
			stateMachinePanel.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩ catch ÉuÉçÉbÉN
				e.printStackTrace();
			}
		}
	}

	static public void main(String[] args) {
		new StateMachinePanelTest();
	}
}
