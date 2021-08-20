package FSM;

import FSM.api.Event;
import FSM.api.State;

import java.util.Map;
import java.util.UUID;

/**
 * @author JiangZhenLi
 */
public class StateTransitionTable {

    private String tableId;

    /**
     * 状态转移表
     */
    private Map<StateEventTuple, StateActionTuple> mappings;

    private StateTransitionTable(String tableId, Map<StateEventTuple, StateActionTuple> mappings) {
        this.tableId = tableId;
        this.mappings = mappings;
    }

    // 匹配状态和事件对应的表项
    StateActionTuple match(State currentState, Event event) {
        return mappings.get(StateEventTuple.create(currentState, event));
    }

    // 使用已有的映射对象创建一个状态转移表
    public static StateTransitionTable create(Map<StateEventTuple, StateActionTuple> mappings) {
        String id = UUID.randomUUID().toString();
        return new StateTransitionTable(id, mappings);
    }


    public String getTableId() {
        return tableId;
    }

}
