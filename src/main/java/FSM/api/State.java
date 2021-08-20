package FSM.api;

/**
 * @author JiangZhenLi
 *
 * 状态的抽象接口，状态机的状态需要实现该接口
 */
public interface State {

    boolean equals(Object object);

    int hashCode();
}
