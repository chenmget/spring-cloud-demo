package com.iwhalecloud.retail.warehouse.dto.request.markres.base;

import lombok.Data;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 营销资源接口请求基类
 *
 * @author 吴良勇
 * @date 2019/3/2 9:42
 */
@Data
public abstract class AbstractMarkResRequest  implements Serializable {
}
