package com.xingmei.netty.core.entity;

import java.io.Serializable;

/**
 * 消息内容响应实体，可以动态的增加属性
 *
 * @Date 2019/10/11 10:25
 * @Auther cheerUpPing@163.com
 */
public class ResponseEntity extends BaseEntity implements Serializable {

    /**
     * 0正常 -1异常
     */
    private int code = 0;

    /**
     * 错误信息
     */
    private String errMsg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
