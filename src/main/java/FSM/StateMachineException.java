package FSM;

/**
 * @author JiangZhenLi
 */
public class StateMachineException extends RuntimeException {

    public static final String NO_TABLE = "没有找到对应的状态迁移表，请检查TableId";
    public static final String STATE_TYPE_ERROR = "状态对象类型不符合要求";
    public static final String EVENT_TYPE_ERROR = "事件对象类型不符合要求";
    public static final String CONTEXT_ID_PARSE_ERROR = "上下文ID解析错误，格式不符合要求";

    StateMachineException(String message) {
        super(message);
    }
}
