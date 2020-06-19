package com.xingmei.netty.core.entity;

import java.io.Serializable;

/**
 * @Date 2019/10/12 17:56
 * @Auther cheerUpPing@163.com
 */
public class BaseEntity implements Serializable {

    private String command;

    private String requestId;

    private String clientIp;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
