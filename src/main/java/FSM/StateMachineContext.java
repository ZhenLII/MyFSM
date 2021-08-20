package FSM;

import FSM.api.Action;
import FSM.api.State;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author JiangZhenLi
 * <p>
 * <p>
 * 需要使用状态机维护状态的上下文
 */
public class StateMachineContext<T> {

    // 锁定上下文对象的锁对象，用于同步状态机执行
    Lock lock = new ReentrantLock();

    // 上下文携带的实体
    private T entity;

    // 状态
    private State state;

    // 上下文的唯一标识
    private StateMachineContextId contextId;

    /**
     * @param entity           上下文维护的实体
     * @param entityIdentifier 实体的唯一标识符
     * @param state            创建上下文时的初态
     * @param tableId          上下文所对应的状态转移表Id
     */
    StateMachineContext(T entity, String entityIdentifier, State state, String tableId) {
        this.entity = entity;
        this.state = state;
        this.contextId = StateMachineContextId.contextId(tableId, entityIdentifier);
    }

    /**
     * @param entity    上下文维护的实体
     * @param state     创建上下文时的初态
     * @param contextId 上下文的唯一标识符
     */
    StateMachineContext(T entity, State state, StateMachineContextId contextId) {
        this.entity = entity;
        this.state = state;
        this.contextId = contextId;
    }

    /**
     * 获得当前上下文状态
     */
    State currentState() {
        return state;
    }


    // 状态转移
    void transferState(State state) {
        this.state = state;
    }

    // 执行传入的动作
    void action(Action action) {
        action.action(this);
    }

    public StateMachineContextId getContextId() {
        return contextId;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }


}
