package com.iwhalecloud.retail.pay.util;

import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil
{
    private Properties props;

    public PropertiesUtil(String fileName)
    {
        readProperties(fileName);
    }

    private void readProperties(String fileName)
    {
        try
        {
            this.props = new Properties();

            InputStreamReader inputStream = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8");
            this.props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key)
    {
        return this.props.getProperty(key);
    }

    public Map<?, ?> getAll()
    {
        Map map = new HashMap();
        Enumeration enu = this.props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String)enu.nextElement();
            String value = this.props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }
}