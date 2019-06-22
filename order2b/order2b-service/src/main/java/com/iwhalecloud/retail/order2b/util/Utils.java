package com.iwhalecloud.retail.order2b.util;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.annotation.EnumCheckValidate;
import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import com.iwhalecloud.retail.order2b.annotation.GroupCheckValidate;
import com.iwhalecloud.retail.order2b.annotation.NullCheckValidate;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    /**
     * @param obj
     */

    public static String objectToString(Object obj) {

        Map<String, Object> paramsMap = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (StringUtils.isEmpty(field.get(obj))) {
                    continue;
                }
                paramsMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }

        return JSON.toJSONString(paramsMap);
    }

    /**
     * 统一增加附件的前缀，多个用逗号，隔开。比如group1/xx.jpg,group1/yy.jpg，增加前缀后http://xxx.com/group1/xx.jpg,http://xxx.com/group1/yy.jpg
     * @param originalUrls 需要增加前缀的url
     * @return
     */
    public static String attacheUrlPrefix(String showUrl,String originalUrls) {
        if (StringUtils.isEmpty(originalUrls)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String[] urls = StringUtils.tokenizeToStringArray(originalUrls,",");
        for (String url : urls) {
            if (!url.startsWith("http")) {
                sb.append(",").append(showUrl).append(url);
            } else {
                sb.append(",").append(url);
            }
        }
        return sb.substring(1).toString();
    }

    /**
     * 统一替换附件的前缀，多个图片用逗号，隔开。比如http://xxx.com/group1/xx.jpg替换后为group1/xx.jpg
     *
     * @param originalUrls 需要替换前缀的url
     * @return 替换后的字符串
     */
    public static String replaceUrlPrefix(String dfsShowIp,String originalUrls) {
        if (org.springframework.util.StringUtils.isEmpty(originalUrls)) {
            return "";
        }

        return originalUrls.replaceAll(dfsShowIp, "");
    }



    public static CommonResultResp validatorCheck(Object request) {
        CommonResultResp result = new CommonResultResp();

        Class c = request.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field fie : fields) {
            try {
                fie.setAccessible(true);
                /**
                 * 非空校验
                 */
                NullCheckValidate nullCheckValidate = fie.getAnnotation(NullCheckValidate.class);
                if (nullCheckValidate != null) {
                    if (StringUtils.isEmpty(fie.get(request))) {
                        result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        result.setResultMsg(nullCheckValidate.message());
                        return result;
                    }
                    continue;
                }

                /**
                 * 组合校验
                 */
                GroupCheckValidate groupCheckValidate = fie.getAnnotation(GroupCheckValidate.class);
                if (groupCheckValidate != null) {
                    Field value = c.getDeclaredField(groupCheckValidate.key());
                    value.setAccessible(true);
                    if (value == null || StringUtils.isEmpty(value.get(request))) {
                        value.setAccessible(false);
                        result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        result.setResultMsg(groupCheckValidate.message());
                        return result;
                    }
                    boolean groupV = checkByGroup(fie, request, value.get(request).toString(), groupCheckValidate);
                    value.setAccessible(false);
                    if (!groupV) {
                        result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        result.setResultMsg(groupCheckValidate.message());
                        return result;
                    }
                }

                /**
                 * 枚举校验
                 */
                EnumCheckValidate enumCheckValidate = fie.getAnnotation(EnumCheckValidate.class);
                if (enumCheckValidate != null) {
                    boolean enumV = checkByEnum(fie, request, enumCheckValidate.enumClass().getName());
                    if (!enumV) {
                        result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        result.setResultMsg(enumCheckValidate.message());
                        return result;
                    }
                    continue;
                }
                fie.setAccessible(false);
            } catch (Exception e) {
                fie.setAccessible(false);
                e.printStackTrace();
                result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                result.setResultMsg("参数校验解析解析异常 code=" + fie.getName());
                return result;
            }
        }
        result.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        result.setResultMsg("参数校验通过");
        return result;

    }

    private static boolean checkByEnum(Field field, Object request, String className) throws Exception {
        if (StringUtils.isEmpty(field.get(request))) {
            return false;
        }
        Class<?> onwClass = null;
        String value = field.get(request).toString();
        onwClass = Class.forName(className);
        Method method = onwClass.getMethod("values");
        EnumInterface[] inter = (EnumInterface[]) method.invoke(null);
        for (EnumInterface enumMessage : inter) {
            String object = enumMessage.getEnumObj();
            if (value.equals(object)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkByGroup(Field field, Object request, String paramsValue, GroupCheckValidate groupCheckValidate) throws Exception {

        if (!paramsValue.equals(groupCheckValidate.value())) {
            return true;
        }
        Object o = field.get(request);
        if (groupCheckValidate.isList()) {
            if (CollectionUtils.isEmpty((Collection<?>) o)) {
                return false;
            }
        } else {
            if (o == null) {
                return false;
            }
        }

        return true;
    }


}
