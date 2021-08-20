package FSM;

import FSM.api.Action;
import FSM.api.Event;
import FSM.api.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

import static FSM.StateMachineException.EVENT_TYPE_ERROR;
import static FSM.StateMachineException.STATE_TYPE_ERROR;


/**
 * @author JiangZhenLi
 */
public class StateTransitionTableBuilder {
    private Logger log = LoggerFactory.getLogger(getClass());

    private Class stateClass = null;

    private Class evnetClass = null;


    public StateTransitionTableBuilder(Class stateClass, Class evnetClass) {
        this.stateClass = stateClass;
        this.evnetClass = evnetClass;
    }

    /**
     * 状态转移表
     */
    private Map<StateEventTuple, StateActionTuple> mappings = new HashMap<>();

    /**
     * 添加表项到状态转移表中
     *
     * @param from   转移前的状态
     * @param on     触发转移的事件
     * @param to     转移后的状态
     * @param action 转移时执行的动作
     */
    public StateTransitionTableBuilder addMapping(State from, Event on, State to, Action action) {

        if (!stateClass.equals(from.getClass()) || !stateClass.equals(to.getClass())) {
            throw new StateMachineException(STATE_TYPE_ERROR);
        }

        if (!evnetClass.equals(on.getClass())) {
            throw new StateMachineException(EVENT_TYPE_ERROR);
        }

        StateEventTuple key = StateEventTuple.create(from, on);
        StateActionTuple value = StateActionTuple.create(to, action);
        if (mappings.containsKey(key)) {
            log.warn("Tuple<{},{}> is already existed,the old one will be repalced!", from, on);
        }
        mappings.put(key, value);
        return this;
    }

    /**
     * 用收集到的表项构建表对象
     */
    public StateTransitionTable build() {
        return StateTransitionTable.create(mappings);
    }
}
