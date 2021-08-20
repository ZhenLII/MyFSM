package FSM.api;

/**
 * @author JiangZhenLi
 *
 * 触发状态转移的事件
 */
public interface Event {

    boolean equals(Object object);

    int hashCode();
}
