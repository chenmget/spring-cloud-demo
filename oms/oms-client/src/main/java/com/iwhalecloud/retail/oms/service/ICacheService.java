package com.iwhalecloud.retail.oms.service;

/**
 * @Author My
 * @Date 2018/11/13
 **/
public interface ICacheService {

   boolean tempAddCart(String key,String value);

   String getTempCart(String key);

   boolean selectTempCart(String key);

}
