package FSM;

import FSM.api.Event;
import FSM.api.State;

/**
 * @author JiangZhenLi
 * <p>
 * <p>
 * (状态,事件)二元组，唯一标识状态转移表中的一个表项
 */
class StateEventTuple {

    // 初态
    private State state;

    //事件
    private Event event;

    private StateEventTuple(State state, Event event) {
        this.state = state;
        this.event = event;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;//地址相等
        }

        if (obj == null) {
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }

        return obj instanceof StateEventTuple &&
                state.equals(((StateEventTuple) obj).state) &&
                event.equals(((StateEventTuple) obj).event);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (state == null ? 0 : state.hashCode());
        result = 31 * result + (event == null ? 0 : event.hashCode());
        return result;
    }

    static StateEventTuple create(State state, Event event) {
        return new StateEventTuple(state, event);
    }
}
