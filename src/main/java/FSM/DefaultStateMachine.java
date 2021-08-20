package FSM;

import FSM.api.Event;
import FSM.api.State;
import FSM.api.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static FSM.StateMachineException.NO_TABLE;


/**
 * @author JiangZhenLi
 */

public class DefaultStateMachine implements StateMachine {
    private Logger log = LoggerFactory.getLogger(getClass());

    //维护所有状态上下文的Map
    private Map<StateMachineContextId, StateMachineContext> contextMap = new ConcurrentHashMap<>();

    // 各个模块的状态转移表Map
    private Map<String, StateTransitionTable> tableMap = new ConcurrentHashMap<>();

    /**
     * 触发状态机运行
     *
     * @param context 状态上下文，携带了自己的表id以及当前状态
     * @param event   触发转移的事件
     */
    public void fire(StateMachineContext context, Event event) {

        // 获取该上下文对应的状态转移表
        StateTransitionTable table = tableMap.get(context.getContextId().getTableId());

        // 如果状态转移表存在，则根据当前状态和事件，进行状态转移
        if (Optional.ofNullable(table).isPresent()) {

            // 在状态转移前先锁定对象
            context.lock.lock();
            State before = context.currentState();
            try {

                StateActionTuple tuple = table.match(context.currentState(), event);

                if (Optional.ofNullable(tuple).isPresent()) {
                    // 如果存在对应的表项，则转移状态
                    context.transferState(tuple.getState());

                    // 如果存在需要执行的动作，则把动作传递给上下文，让上下文对象去执行
                    if (Optional.ofNullable(tuple.getAction()).isPresent()) {
                        context.action(tuple.getAction());
                    } else {
                        log.warn("Action is null,no action will be excuted!");
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                State after = context.currentState();
                log.debug("context:{}, before {} ,on {}, after {}", context.getContextId().getIdentifier(), before, event, after);
                // 最后释放锁
                context.lock.unlock();
            }
        } else {
            throw new StateMachineException(NO_TABLE);
        }
    }

    public void addSst(StateTransitionTable sst) {
        tableMap.put(sst.getTableId(), sst);
    }

    public void removeSst(StateTransitionTable sst) {
        tableMap.remove(sst.getTableId());
    }

    @Override
    public <T> StateMachineContext<T> getOrCreateContext(T entity, String entityIdntifier, State state, String tableId) {
        StateMachineContextId contextId = StateMachineContextId.contextId(tableId, entityIdntifier);
        StateMachineContext<T> context = contextMap.get(contextId);
        if (Optional.ofNullable(context).isPresent()) {
            return context;
        } else {
            context = new StateMachineContext<>(entity, state, contextId);
            contextMap.put(contextId, context);
            return context;
        }
    }

    @Override
    public StateMachineContext getContext(StateMachineContextId contextId) {
        return contextMap.get(contextId);
    }

    @Override
    public void removeContext(StateMachineContextId contextId) {
        contextMap.remove(contextId);
    }

    @Override
    public Boolean containsContext(StateMachineContextId contextId) {
        return contextMap.containsKey(contextId);
    }

}
