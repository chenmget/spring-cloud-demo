package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 分页基础对象.
 *
 * @author Ji.kai
 * @date 2018/10/27 15:27
 */
@ApiModel(value = "分页基础对象")
public class PageVO implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(PageVO.class);
    private static final long serialVersionUID = -6550827640887727324L;
    @ApiModelProperty(value = "页码")
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;
    @ApiModelProperty(value = "排序字段")
    private String sortName;
    @ApiModelProperty(value = "排序方向")
    private String sortOrder;

    public void setSortName(String sortName) {
        if (sortName != null) {
            if (sortName.indexOf(";") > 0) {
                log.warn("排序字段中含有;号，可能是sql注入， sortName={}, 过滤掉这个排序内容", sortName);
            } else {
                this.sortName = sortName;
            }
        }
    }

    public String getSortName() {
        if (this.sortName == null) {
            return null;
        } else if (this.sortName.indexOf(";") > 0) {
            log.warn("排序字段中含有;号，可能是sql注入， sortName={}, 过滤掉这个排序内容", this.sortName);
            return null;
        } else {
            return this.sortName;
        }
    }

    public void setSortOrder(String sortOrder) {
        if (sortOrder != null) {
            if (!"desc".equalsIgnoreCase(sortOrder) && !"asc".equalsIgnoreCase(sortOrder)) {
                log.warn("排序方向字段值不正确， sortOrder={}, 忽略排序方向", sortOrder);
            } else {
                this.sortOrder = sortOrder;
            }
        }
    }

    public String getSortOrder() {
        if (this.sortOrder == null) {
            return null;
        } else if (!"desc".equalsIgnoreCase(this.sortOrder) && !"asc".equalsIgnoreCase(this.sortOrder)) {
            log.warn("排序方向字段值不正确， sortOrder={}, 忽略排序方向", this.sortOrder);
            return null;
        } else {
            return this.sortOrder;
        }
    }

    public PageVO() {
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageVO)) {
            return false;
        } else {
            PageVO other = (PageVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$pageNum = this.getPageNo();
                    Object other$pageNum = other.getPageNo();
                    if (this$pageNum == null) {
                        if (other$pageNum == null) {
                            break label59;
                        }
                    } else if (this$pageNum.equals(other$pageNum)) {
                        break label59;
                    }

                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$sortName = this.getSortName();
                Object other$sortName = other.getSortName();
                if (this$sortName == null) {
                    if (other$sortName != null) {
                        return false;
                    }
                } else if (!this$sortName.equals(other$sortName)) {
                    return false;
                }

                Object this$sortOrder = this.getSortOrder();
                Object other$sortOrder = other.getSortOrder();
                if (this$sortOrder == null) {
                    if (other$sortOrder != null) {
                        return false;
                    }
                } else if (!this$sortOrder.equals(other$sortOrder)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageVO;
    }

    @Override
    public String toString() {
        return "PageVO(pageNo=" + this.getPageNo() + ", pageSize=" + this.getPageSize() + ", sortName=" + this.getSortName() + ", sortOrder=" + this.getSortOrder() + ")";
    }
}
