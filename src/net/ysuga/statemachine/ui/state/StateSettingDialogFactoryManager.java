/**
 * StateSettingDiagramFactoryManager.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author ysuga
 *
 */
public class StateSettingDialogFactoryManager {

	static Logger logger;
	
	static {
		logger = Logger.getLogger("net.ysuga.statemachine.state.StateFactoryManager");
	}
	
	private Map<String, StateSettingDialogFactory> stateSettingDialogFactoryMap;
	
	final public Set<String> getKindList() {
		Set<String> kindList = new HashSet<String>();
		
		kindList.addAll(stateSettingDialogFactoryMap.keySet());
		return kindList;
	}
	
	static private StateSettingDialogFactoryManager instance;
	
	static final public StateSettingDialogFactoryManager getInstance() {
		if(instance == null) {
			instance = new StateSettingDialogFactoryManager();
		}
		
		return instance;
	}
	
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	private StateSettingDialogFactoryManager() {
		super();
		stateSettingDialogFactoryMap = new HashMap<String, StateSettingDialogFactory>();
	}

	/**
	 * <div lang="ja">
	 * @param kind
	 * @param factory
	 * </div>
	 * <div lang="en">
	 * @param kind
	 * @param factory
	 * </div>
	 */
	static public void add(StateSettingDialogFactory factory) {
		StateSettingDialogFactoryManager manager = StateSettingDialogFactoryManager.getInstance();
		logger.entering(StateSettingDialogFactoryManager.class.getName(), "add", factory);
		manager.stateSettingDialogFactoryMap.put(factory.getKind(), factory);
	}

	/**
	 * <div lang="ja">
	 * @param kind
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param kind
	 * @return
	 * @throws Exception
	 * </div>
	 */
	public StateSettingDialogFactory get(String kind) {
		StateSettingDialogFactory factory = stateSettingDialogFactoryMap.get(kind);
		return factory;
	}

	/**
	 * <div lang="ja">
	 * @param kind
	 * </div>
	 * <div lang="en">
	 * @param kind
	 * </div>
	 */
	public void remove(String kind) {
		stateSettingDialogFactoryMap.remove(kind);
	}

	/**
	 * <div lang="ja">
	 * @param factory
	 * </div>
	 * <div lang="en">
	 * @param factory
	 * </div>
	 */
	public void remove(StateSettingDialogFactory factory) {
		stateSettingDialogFactoryMap.remove(factory.getKind());
	}

}
