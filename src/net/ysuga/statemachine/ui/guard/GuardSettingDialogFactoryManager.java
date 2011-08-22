/**
 * StateSettingDiagramFactoryManager.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/09
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.ui.guard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author ysuga
 *
 */
public class GuardSettingDialogFactoryManager {

	static Logger logger;
	
	static {
		logger = Logger.getLogger(GuardSettingDialogFactoryManager.class.getName());
	}
	
	private Map<String, GuardSettingDialogFactory> guardSettingDialogFactoryMap;
	
	final public Set<String> getKindList() {
		Set<String> kindList = new HashSet<String>();
		
		kindList.addAll(guardSettingDialogFactoryMap.keySet());
		return kindList;
	}
	
	static private GuardSettingDialogFactoryManager instance;
	
	static final public GuardSettingDialogFactoryManager getInstance() {
		if(instance == null) {
			instance = new GuardSettingDialogFactoryManager();
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
	private GuardSettingDialogFactoryManager() {
		super();
		guardSettingDialogFactoryMap = new HashMap<String, GuardSettingDialogFactory>();
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
	static public void add(GuardSettingDialogFactory factory) {
		GuardSettingDialogFactoryManager manager = GuardSettingDialogFactoryManager.getInstance();
		logger.entering(GuardSettingDialogFactoryManager.class.getName(), "add", factory);
		manager.guardSettingDialogFactoryMap.put(factory.getKind(), factory);
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
	public GuardSettingDialogFactory get(String kind) {
		GuardSettingDialogFactory factory = guardSettingDialogFactoryMap.get(kind);
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
		guardSettingDialogFactoryMap.remove(kind);
	}

	/**
	 * <div lang="ja">
	 * @param factory
	 * </div>
	 * <div lang="en">
	 * @param factory
	 * </div>
	 */
	public void remove(GuardSettingDialogFactory factory) {
		guardSettingDialogFactoryMap.remove(factory.getKind());
	}

}
