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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.StateMachineExecutionThread;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.ui.guard.AndGuardSettingDialogFactory;
import net.ysuga.statemachine.ui.guard.DelayGuardSettingDialogFactory;
import net.ysuga.statemachine.ui.guard.ExorGuardSettingDialogFactory;
import net.ysuga.statemachine.ui.guard.GuardSettingDialogFactoryManager;
import net.ysuga.statemachine.ui.guard.NotGuardSettingDialogFactory;
import net.ysuga.statemachine.ui.guard.NullGuardSettingDialogFactory;
import net.ysuga.statemachine.ui.guard.OrGuardSettingDialogFactory;
import net.ysuga.statemachine.ui.shape.StateMachineShape;
import net.ysuga.statemachine.ui.shape.StateMachineShapeBuilder;
import net.ysuga.statemachine.ui.shape.TransitionPopupMenu;
import net.ysuga.statemachine.ui.state.StatePopupMenu;

import org.xml.sax.SAXException;

/**
 * 
 * <div lang="ja"> StateMachineクラスを表示および管理するためのパネルクラス．
 * 
 * </div> <div lang="en"> Panel Class for controlling StateMachine class </div>
 * 
 * @author ysuga
 * 
 * @see StateMachine
 */
public class StateMachinePanel extends JPanel {

	public final static int EDIT_NORMAL = 0;
	public final static int EDIT_TRANSITION = 1;
	
	public int editMode = EDIT_NORMAL;
	
	public int getEditMode() {
		return editMode;
	}
	
	public void setEditMode(int mode) {
		editMode = mode;
	}
	
	
	/**
	 * File Extention of FSM
	 */
	public static final String FSM = "fsm";

	/**
	 * StateMachine class object.
	 */
	private StateMachine stateMachine;
	
	private StateMachineExecutionThread stateMachineExecutionThread;

	/**
	 * 
	 * <div lang="ja"> StateMachineの取得
	 * 
	 * @return Panelが保持するStateMachine </div> <div lang="en"> getter for
	 *         StateMachine owned by this panel.
	 * @return </div>
	 */
	public StateMachine getStateMachine() {
		return stateMachine;
	}

	/**
	 * StateMachineShape. if repainted, automatically re-constructed. Do not use
	 * to store any datas in this object because it will be destroyed and
	 * reconstructed if repainted.
	 */
	private StateMachineShape stateMachineShape;

	/**
	 * 
	 * getStateMachineShape <div lang="ja">
	 * 
	 * @return </div> <div lang="en"> getter function for StateMachine shape
	 * @return </div>
	 */
	public StateMachineShape getStateMachineShape() {
		return stateMachineShape;
	}

	/**
	 * selected state (state can be selected by clicking)
	 */
	private State selectedState;

	/**
	 * 
	 * getSelectedState <div lang="ja"> 選択中のStateの取得
	 * 
	 * @return 選択中のState.なければnull </div> <div lang="en"> getter function for
	 *         selected state
	 * @return State object. If not selected, null will be returned. </div>
	 */
	public State getSelectedState() {
		return selectedState;
	}

	/**
	 * 
	 * setSelectedState <div lang="ja"> 選択中のStateの設定．自動的に選択中のTransitionはnullになる．
	 * 
	 * @param state
	 *            </div> <div lang="en"> Setting selected state. Selected
	 *            Transition will be null automatically.
	 * @param state
	 *            </div>
	 */
	public void setSelectedState(State state) {
		selectedTransition = null;
		selectedState = state;
	}

	/**
	 * Selected Transition
	 */
	private Transition selectedTransition;

	/**
	 * 
	 * getSelectedTransition <div lang="ja">
	 * 
	 * @return </div> <div lang="en"> getter function for selected transition
	 *         object.
	 * @return selected transition object. If no transition is selected, null
	 *         will be returned. </div>
	 */
	public Transition getSelectedTransition() {
		return selectedTransition;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param transition
	 *            </div> <div lang="en">
	 * 
	 * @param transition
	 *            </div>
	 */
	public void setSelectedTransition(Transition transition) {
		selectedTransition = transition;
		selectedState = null;
	}

	/**
	 * Selected Pivot
	 */
	private Point selectedPivot;

	public void setSelectedPivot(Point p) {
		selectedPivot = p;
	}

	public Point getSelectedPivot() {
		return selectedPivot;
	}

	private StatePopupMenu statePopupMenu;

	public StatePopupMenu getStatePopupMenu() {
		return statePopupMenu;
	}

	private StateMachinePanelPopupMenu stateMachinePanelPopupMenu;
	private TransitionPopupMenu transitionPopupMenu;

	/**
	 * 
	 * <div lang="ja"> コンストラクタ
	 * 
	 * @param stateMachine
	 *            </div> <div lang="en"> Constructor
	 * @param stateMachine
	 *            </div>
	 * @throws ParserConfigurationException 
	 */
	public StateMachinePanel() throws ParserConfigurationException {
		this.stateMachine = createStateMachine("new state machine");
		this.stateMachineShape = StateMachineShapeBuilder
				.buildStateMachineShape(stateMachine);

		StateMachinePanelMouseAdapter adapter = new StateMachinePanelMouseAdapter(
				this);
		super.addMouseListener(adapter);
		super.addMouseMotionListener(adapter);


		this.statePopupMenu = new StatePopupMenu(this);
		this.transitionPopupMenu = new TransitionPopupMenu(this);
		this.stateMachinePanelPopupMenu = new StateMachinePanelPopupMenu(this);

		GuardSettingDialogFactoryManager
				.add(new AndGuardSettingDialogFactory());
		GuardSettingDialogFactoryManager
				.add(new ExorGuardSettingDialogFactory());
		GuardSettingDialogFactoryManager.add(new OrGuardSettingDialogFactory());
		GuardSettingDialogFactoryManager
				.add(new NotGuardSettingDialogFactory());

		GuardSettingDialogFactoryManager
				.add(new DelayGuardSettingDialogFactory());
		GuardSettingDialogFactoryManager
			.add(new NullGuardSettingDialogFactory());
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @param g
	 *            </div> <div lang="en">
	 * @param g
	 *            </div>
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = this.getSize();
		Rectangle2D.Double r = new Rectangle2D.Double(0, 0, d.width, d.height);
		g2d.setColor(Color.white);
		g2d.fill(r);
		g2d.setColor(Color.black);

		this.stateMachineShape = StateMachineShapeBuilder
				.buildStateMachineShape(stateMachine);
		stateMachineShape.setSelectedState(selectedState);
		stateMachineShape.setSelectedTransition(selectedTransition);
		stateMachineShape.draw(g);
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * 
	 * @return </div>
	 */
	public StateMachinePanelPopupMenu getPopupMenu() {
		return stateMachinePanelPopupMenu;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * 
	 * @return </div>
	 */
	public TransitionPopupMenu getTransitionPopupMenu() {
		return transitionPopupMenu;
	}

	class FSMFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(java.io.File f) {
			if (f.isDirectory())
				return true;
			String name = f.getName().toLowerCase();
			if (name.endsWith(FSM))
				return true;
			return false;
		}

		public java.lang.String getDescription() {
			return "*." + FSM;
		}
	}

	public final File showOpenFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("Open file");
		fileChooser.setFileFilter(new FSMFileFilter());
		int ret = fileChooser.showOpenDialog(this);
		if (ret != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File file = fileChooser.getSelectedFile();
		if (!file.exists()) {
			JOptionPane.showMessageDialog(this, "File not exists.");
			return null;
		}
		if (!file.getName().endsWith(FSM)) {
			JOptionPane.showMessageDialog(this, "File is not available.");
			return null;
		}

		StateMachine oldStateMachine = this.stateMachine;
		try {
			this.stateMachine = createStateMachine(file);
		} catch (InvalidFSMFileException e) {
			JOptionPane.showMessageDialog(this, "Invalid FSM file.");
			this.stateMachine = oldStateMachine;
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(this, "Invalid FSM file.");
			this.stateMachine = oldStateMachine;
			e.printStackTrace();
		} catch (SAXException e) {
			JOptionPane.showMessageDialog(this, "Invalid FSM file.");
			this.stateMachine = oldStateMachine;
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Invalid FSM file.");
			this.stateMachine = oldStateMachine;
			e.printStackTrace();
		}
		repaint();
		return file;
	}

	public final File showSaveFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("Select File");
		fileChooser.setFileFilter(new FSMFileFilter());
		if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File file = fileChooser.getSelectedFile();
		if (!file.getName().endsWith(FSM)) {
			file = new File(file.getAbsolutePath() + "." + FSM);
		}

		if (file.exists()) {
			if (JOptionPane.showConfirmDialog(this, "Overwrite?") == JOptionPane.NO_OPTION) {
				return null;
			}
		}

		try {
			this.stateMachine.save(file);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Failed to save.");
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * createNewStateMachine <div lang="ja">
	 * 
	 * </div> <div lang="en">
	 * 
	 * </div>
	 */
	public void createNewStateMachine() {

		try {
			String name = JOptionPane.showInputDialog(this,
					"Input New State Machine Name");
			if (name != null) {
				stateMachine = createStateMachine("new state machine");
			} else {
				JOptionPane.showMessageDialog(this,
						"Creating New State Machine is canceled.");
			}
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		repaint();
	}

	/**
	 * createStateMachine
	 * <div lang="ja">
	 * 
	 * @param string
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param string
	 * @return
	 * </div>
	 * @throws ParserConfigurationException 
	 */
	public StateMachine createStateMachine(String string) throws ParserConfigurationException {
		return new StateMachine(string);
	}

	public StateMachine createStateMachine(File file) throws InvalidFSMFileException, ParserConfigurationException, SAXException, IOException {
		return new StateMachine(file);
	}

	/**
	 * start
	 * <div lang="ja">
	 * 
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void start() {
		stateMachineExecutionThread = new StateMachineExecutionThread(stateMachine);
		stateMachineExecutionThread.startExecution();
	}

	/**
	 * stop
	 * <div lang="ja">
	 * 
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void stop() {
		stateMachineExecutionThread.stopExecution();
		stateMachineExecutionThread = null;
		stateMachine.reset();
	}
	/**
	 * suspend
	 * <div lang="ja">
	 * 
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void suspend() {
		stateMachineExecutionThread.suspendExecution();
	}


	/**
	 * resume
	 * <div lang="ja">
	 * 
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void resume() {
		stateMachineExecutionThread.resumeExecution();
	}
}
