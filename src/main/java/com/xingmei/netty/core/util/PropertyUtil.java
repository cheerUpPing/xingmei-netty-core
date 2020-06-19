package com.xingmei.netty.core.util;

import com.xingmei.netty.core.entity.NettyConstant;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Date 2019/10/15 18:15
 * @Auther cheerUpPing@163.com
 */
public class PropertyUtil {

    /**
     * 保存{@link Properties}对象到.properties文件，如果原来存在这个文件那么就被全部覆盖
     *
     * @param properties
     * @param parentDir  父目录
     * @param fileName   文件名,包含文件拓展名
     * @throws IOException
     */
    public static void saveProperty(Properties properties, String parentDir, String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(parentDir, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = ((String) enumeration.nextElement()).replaceAll("_", ".").replaceAll("\\$", ".");
            String val = properties.getProperty(key);
            sb.append(key).append(" = ").append(val).append(NettyConstant.lineSeparator);
        }
        IOUtils.write(sb.toString(), fileOutputStream, "UTF-8");
    }

    /**
     * 保存{@link Map<String, Object>}对象到.properties文件，如果原来存在这个文件那么就被全部覆盖
     *
     * @param property
     * @param parentDir 父目录
     * @param fileName  文件名,包含文件拓展名
     * @throws IOException
     */
    public static void saveProperty(Map<String, Object> property, String parentDir, String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(parentDir, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        if (MapUtils.isNotEmpty(property)) {
            for (Map.Entry<String, Object> entry : property.entrySet()) {
                String key = entry.getKey().replaceAll("_", ".").replaceAll("\\$", ".");
                String val = entry.getValue().toString();
                sb.append(key).append(" = ").append(val).append(NettyConstant.lineSeparator);
            }
        }
        IOUtils.write(sb.toString(), fileOutputStream, "UTF-8");
    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("aaa", "bbb");
        map.put("aaac", "0");
        map.put("aaacb", "1:3");
        PropertyUtil.saveProperty(map, "d:\\", "333.properties");
    }
}
