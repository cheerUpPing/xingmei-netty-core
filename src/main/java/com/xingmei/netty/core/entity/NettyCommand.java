package com.xingmei.netty.core.entity;

import java.io.Serializable;

/**
 * netty传输控制命令
 *
 * @Date 2019/7/24 16:23
 * @Auther cheerUpPing@163.com
 */
public enum NettyCommand implements Serializable {

    /**
     * 重启canal
     */
    RE_START_CANAL("re_start_canal"),

    /**
     * 启动canal
     */
    START_CANAL("start_canal"),

    /**
     * 停止canal
     */
    STOP_CANAL("stop_canal"),

    /**
     * 启用数据源
     */
    ENABLE_DATA_SOURCE("enable_data_source"),

    /**
     * 禁用数据源
     */
    NOT_ENABLE_DATA_SOURCE("not_enable_data_source"),

    /**
     * 更新数据源(数据源启用状态下更新)
     */
    UPDATE_DATA_SOURCE("update_data_source"),

    /**
     * 初始化canal主机
     */
    INIT_CANAL_HOST("init_canal_host"),

    /**
     * 更新canal群配置
     */
    UPDATE_CANAL_CLUSTER_CONFIG("update_canal_cluster_config");

    private String command;

    NettyCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
