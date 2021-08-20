package FSM;

import FSM.api.Action;
import FSM.api.State;

/**
 * @author JiangZhenLi
 * <p>
 * <p>
 * 状态转移表的每一个表项
 */
class StateActionTuple {

    /**
     * 状态
     */
    private State state;

    /**
     * 动作
     */
    private Action action;

    private StateActionTuple(State state, Action action) {
        this.state = state;
        this.action = action;
    }

    State getState() {
        return state;
    }

    Action getAction() {
        return action;
    }

    static StateActionTuple create(State state, Action action) {
        return new StateActionTuple(state, action);
    }


}
