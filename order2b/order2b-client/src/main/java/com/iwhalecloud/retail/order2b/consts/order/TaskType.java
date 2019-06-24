package com.iwhalecloud.retail.order2b.consts.order;

import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

/**
 * 任务类型
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月26日
 */
public enum TaskType implements EnumInterface {

    NULL("默认","0"),
    TASK_TYPE_1("流程","1"),
    TASK_TYPE_2("工单","2"),

    TASK_SUB_TYPE_WAIT("待买家确认","WAIT")
    ;



    public static TaskType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return TaskType.NULL;
        }
        for (TaskType opCode : TaskType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return TaskType.NULL;
    }

    TaskType(String name, String code) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEnumObj() {
        return getCode();
    }
}
