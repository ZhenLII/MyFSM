package FSM;


import java.util.Objects;

import static FSM.StateMachineException.CONTEXT_ID_PARSE_ERROR;

/**
 * @author JiangZhenLi
 */
public class StateMachineContextId {

    private final static String SLASH = "/";

    // 对应状态转移表id
    private String tableId;

    // 上下文对象在表内的唯一标识符，一般使用上下文对象中包含的实体的唯一标识符表示
    private String identifier;

    // 表id和标识符组成的字符串，在整个状态机内唯一标识一个上下文
    private String str;

    private StateMachineContextId(String tableId, String identifier) {
        if (tableId == null || tableId.isBlank() || identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException("TableId or identifier could not be blank");
        }
        this.tableId = tableId;
        this.identifier = identifier;
        str = tableId.concat(SLASH).concat(identifier);
    }

    public static StateMachineContextId contextId(String tableId, String identifier) {
        return new StateMachineContextId(tableId, identifier);
    }

    public static StateMachineContextId contextId(String str) {
        String[] ids = str.split(SLASH);
        if (ids.length != 2) {
            throw new StateMachineException(CONTEXT_ID_PARSE_ERROR);
        } else {
            String tableId = ids[0];
            String identifier = ids[1];
            return new StateMachineContextId(tableId, identifier);
        }
    }

    public String getTableId() {
        return tableId;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public int hashCode() {
        return str.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StateMachineContextId) {
            final StateMachineContextId that = (StateMachineContextId) obj;
            return this.getClass() == that.getClass() &&
                    Objects.equals(this.str, that.str);
        }
        return false;
    }
}
