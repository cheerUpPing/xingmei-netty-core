package com.xingmei.netty.core.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息内容请求实体，可以动态的增加属性
 *
 * @Date 2019/10/11 10:25
 * @Auther cheerUpPing@163.com
 */
public class RequestEntity extends BaseEntity implements Serializable {

    private Map<String, Object> param;

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}
