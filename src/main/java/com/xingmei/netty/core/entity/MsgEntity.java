package com.xingmei.netty.core.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 自定义netty协议
 *
 * @Date 2019/7/24 15:26
 * @Auther cheerUpPing@163.com
 */
public class MsgEntity implements Serializable {

    /**
     * 消息的长度,单位：字节
     */
    private int contentLength;

    /**
     * 消息的内容
     */
    private String content;

    /**
     * 用于初始化，MsgEntity
     *
     * @param contentRequestEntity 协议里面，消息的数据
     */
    public MsgEntity(RequestEntity contentRequestEntity) {
        String msg  = JSON.toJSONString(contentRequestEntity);
        this.contentLength = msg.getBytes().length;
        this.content = msg;
    }

    public MsgEntity(ResponseEntity contentResponseEntity) {
        String msg  = JSON.toJSONString(contentResponseEntity);
        this.contentLength = msg.getBytes().length;
        this.content = msg;
    }

    public MsgEntity(int contentLength, String content) {
        this.contentLength = contentLength;
        this.content = content;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MsgEntity{" +
                "contentLength=" + contentLength +
                ", content='" + content + '\'' +
                '}';
    }
}
