package FSM.api;


import FSM.StateMachineContext;
import FSM.StateMachineContextId;
import FSM.StateTransitionTable;

/**
 * @author JiangZhenLi
 */
public interface StateMachine {

    /**
     * 触发状态转移
     *
     * @param context 状态上下文，携带状态信息
     * @param event   事件
     */
    void fire(StateMachineContext context, Event event);

    // 添加状态转移表
    void addSst(StateTransitionTable sst);

    // 删除状态转移表
    void removeSst(StateTransitionTable sst);

    // 获取或者创建状态上下文，如果有就获取，没有则创建
    <T> StateMachineContext<T> getOrCreateContext(T entity, String entityIdntifier, State state, String tableId);

    // 获取状态上下文，没有返回空
    StateMachineContext getContext(StateMachineContextId contextId);

    // 移除状态上下文
    void removeContext(StateMachineContextId contextId);

    // 判断状态上下文是否存在
    Boolean containsContext(StateMachineContextId contextId);
}
