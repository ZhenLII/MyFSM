package FSM.api;

import FSM.StateMachineContext;

/**
 * @author JiangZhenLi
 *
 * 状态转移时同时执行的动作
 */
@FunctionalInterface
public interface Action {
     void action(StateMachineContext context);
}
