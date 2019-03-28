package com.iwhalecloud.retail.workflow.consts;

import org.springframework.util.StringUtils;

/**
 * node_role(type)
 */
public enum NodeRoleType {

    NULL("","",""),
    NODE_ROLE_TYPE_0("流程发起人","0",""),
    NODE_ROLE_TYPE_1("角色配置","1",""),
    NODE_ROLE_TYPE_2("指定用户","2",""),
            ;


    public static NodeRoleType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return NodeRoleType.NULL;
        }
        for (NodeRoleType opCode : NodeRoleType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return NodeRoleType.NULL;
    }

    NodeRoleType(String name, String code, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
