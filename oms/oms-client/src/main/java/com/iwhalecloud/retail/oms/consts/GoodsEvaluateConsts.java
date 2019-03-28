package com.iwhalecloud.retail.oms.consts;

public enum GoodsEvaluateConsts {
    ZJ_H(1, "质量好"),
    KD_H(2, "快递不错"),
    XJB_H(3, "性价比高");

    GoodsEvaluateConsts(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    private Integer code;
    private String codeName;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
