/**
 * StateMachinePanelMouseAdapter.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import net.ysuga.statemachine.transition.PivotList;
import net.ysuga.statemachine.ui.shape.TransitionShape;
import net.ysuga.statemachine.ui.shape.base.PivottedNamedArrow;
import net.ysuga.statemachine.ui.shape.state.DefaultStateShape;
import net.ysuga.statemachine.ui.shape.state.StateShape;

/**
 * @author ysuga
 * 
 */
public class StateMachinePanelMouseAdapter implements MouseListener,
		MouseMotionListener {

	private StateMachinePanel panel;

	private Point selectOffset;

	public void setSelectedOffset(Point point) {
		selectOffset = point;
	}

	public Point getSelectOffset() {
		return selectOffset;
	}

	public StateMachinePanelMouseAdapter(StateMachinePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent arg0) {
		panel.setSelectedState(null);
		for (StateShape stateShape : panel.getStateMachineShape()
				.getStateShapeList()) {
			if (stateShape.contains(arg0.getPoint())) {
				panel.setSelectedState(stateShape.getState());
				int dx = (int) (arg0.getPoint().x - stateShape.getX());
				int dy = (int) (arg0.getPoint().y - stateShape.getY());
				setSelectedOffset(new Point(dx, dy));
				stateShape.onClicked(panel, arg0);
				return;
			}
		}

		for (TransitionShape transitionShape : panel.getStateMachineShape()
				.getTransitionShapeList()) {
			if (transitionShape.contains(arg0.getPoint())) {
				transitionShape.setSelected(true);
				panel.setSelectedTransition(transitionShape.getTransition());

				if (arg0.getButton() == MouseEvent.BUTTON3) { // RightClick
					panel.getTransitionPopupMenu().show(panel, arg0.getPoint());
				}
				
				if(arg0.getButton() == MouseEvent.BUTTON1 && arg0.getClickCount() == 2) {
					PivotList list = transitionShape.getTransition().getPivotList();
					Point nearestPivot = null;
					Point mouse = arg0.getPoint();
					for(Point pivot : list) {
						int dx = pivot.x - mouse.x;
						int dy = pivot.y - mouse.y;
						double distance = Math.sqrt(dx*dx + dy*dy);
						if(distance < 10) {
							nearestPivot = pivot;
						}

					}
					if(nearestPivot != null) {
						list.remove(nearestPivot);
					}
				}
				return;
			}
		}

		if (arg0.getButton() == MouseEvent.BUTTON3) { // RightClick
			panel.getPopupMenu().show(panel, arg0.getPoint());
		}
		panel.repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {
		panel.setSelectedState(null);
		for (StateShape stateShape : panel.getStateMachineShape()
				.getStateShapeList()) {
			if (stateShape.contains(arg0.getPoint())) {
				panel.setSelectedState(stateShape.getState());
				int dx = (int) (arg0.getPoint().x - stateShape.getX());
				int dy = (int) (arg0.getPoint().y - stateShape.getY());
				setSelectedOffset(new Point(dx, dy));

				panel.repaint();

				return;
			}
		}

		if (arg0.getButton() == MouseEvent.BUTTON1) {
			for (TransitionShape transitionShape : panel.getStateMachineShape()
					.getTransitionShapeList()) {
				if (transitionShape.contains(arg0.getPoint())) {
					panel.setSelectedTransition(transitionShape.getTransition());

					Point mp = arg0.getPoint();
					panel.setSelectedPivot(null);
					// PivotList pivotList = transition.getPivotList();
					for (Point p : transitionShape.getTransition()
							.getPivotList()) {
						double dist = Math.sqrt((p.x - mp.x) * (p.x - mp.x)
								+ (p.y - mp.y) * (p.y - mp.y));
						if (dist < 10) {
							panel.setSelectedPivot(p);
						}
					}
					if (panel.getSelectedPivot() == null) {
						panel.setSelectedPivot(new Point(mp.x, mp.y));

						Point oldPoint = transitionShape.getArrow()
								.getStartPoint();
						for (int i = 0; i < transitionShape.getTransition()
								.getPivotList().size(); i++) {
							Point p = transitionShape.getTransition()
									.getPivotList().get(i);
							if (PivottedNamedArrow.lineContains(oldPoint.x,
									oldPoint.y, p.x, p.y, mp)) {
								transitionShape.getTransition().getPivotList()
										.add(i, panel.getSelectedPivot());
								return;
							}
							oldPoint = p;
						}
						transitionShape.getTransition().getPivotList()
								.add(panel.getSelectedPivot());
					}
				}
			}
		} else {
			for (TransitionShape transitionShape : panel.getStateMachineShape()
					.getTransitionShapeList()) {
				if (transitionShape.contains(arg0.getPoint())) {
					transitionShape.setSelected(true);
					panel.setSelectedTransition(transitionShape.getTransition());

					if (arg0.getButton() == MouseEvent.BUTTON3) { // RightClick
						panel.getTransitionPopupMenu().show(panel, arg0.getPoint());
					}
					return;
				}
			}
		}

		if (arg0.getButton() == MouseEvent.BUTTON3) { // RightClick
			panel.getPopupMenu().show(panel, arg0.getPoint());
		}

		panel.repaint();

	}

	public void mouseReleased(MouseEvent arg0) {
		/**
		 * ModelDiagramElement fromElement = null; for
		 * (ModelDiagramElementHolder modelDiagramElementHolder :
		 * modelDiagramElementHolderList) { Set<String> keySet =
		 * modelDiagramElementHolder.keySet(); for(String key : keySet) {
		 * ModelDiagramElement elem = modelDiagramElementHolder.get(key);
		 * if(elem.isRButtonCapturing() || elem.isLButtonCapturing()) {
		 * elem.onMouseReleased(arg0); fromElement = elem; // repaint(); } } }
		 * 
		 * 
		 * 
		 * for (ModelDiagramElementHolder modelDiagramElementHolder :
		 * modelDiagramElementHolderList) { ModelDiagramElement
		 * modelDiagramElement = modelDiagramElementHolder
		 * .getContainingObject(arg0); if (modelDiagramElement != null) {
		 * if(fromElement != null) { modelDiagramElement.onMouseDroped(arg0,
		 * fromElement); } } } repaint();
		 */

		
		for (TransitionShape transitionShape : panel.getStateMachineShape()
				.getTransitionShapeList()) {
			if (panel.getSelectedTransition() != null
					&& transitionShape.contains(arg0.getPoint())) {
				panel.setSelectedTransition(transitionShape.getTransition());

				Point prevPoint, nextPoint;

				PivotList pivotList = transitionShape.getTransition()
						.getPivotList();
				int selectedIndex = pivotList.indexOf(panel.getSelectedPivot());
				if (selectedIndex < 0) {
					continue;
				} else if (selectedIndex == 0) { // firstPivot
					prevPoint = transitionShape.getArrow().getStartPoint();
				} else {
					prevPoint = pivotList.get(selectedIndex - 1);
				}

				if (selectedIndex == pivotList.size() - 1) { // lastPivot
					nextPoint = transitionShape.getArrow().getEndPoint();
				} else {
					nextPoint = pivotList.get(selectedIndex + 1);
				}
				if (PivottedNamedArrow.lineContains(prevPoint.x, prevPoint.y,
						nextPoint.x, nextPoint.y, panel.getSelectedPivot())) {
					pivotList.remove(panel.getSelectedPivot());
				}
				panel.setSelectedPivot(null);
			}
		}

		panel.repaint();
	}

	public void mouseDragged(MouseEvent arg0) {
		if (panel.getSelectedState() != null) {
			int x = arg0.getPoint().x - getSelectOffset().x;
			int y = arg0.getPoint().y - getSelectOffset().y;
			if(x < 0) x = 0;
			if(y < 0) y = 0;
			panel.getSelectedState().setLocation(x,y);
		}

		// for(TransitionShape transitionShape :
		// panel.getStateMachineShape().getTransitionShapeList()) {
		// if(transitionShape.contains(arg0.getPoint())) {
		if (panel.getSelectedTransition() != null) {
			Point mp = arg0.getPoint();
			int x = mp.x;
			int y = mp.y;
			if(x < 0) x = 0;
			if(y < 0) y = 0;
			if(panel.getSelectedPivot() != null) {
				panel.getSelectedPivot().setLocation(x, y);
			}
		}
		panel.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {

	}
}
