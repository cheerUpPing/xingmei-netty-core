package com.xingmei.netty.core.handler;

import com.xingmei.netty.core.entity.RequestEntity;

/**
 * 业务处理器
 *
 * @Date 2019/7/25 14:26
 * @Auther cheerUpPing@163.com
 */
public abstract class BaseProcess {

    /**
     * 该方法不向外面抛出异常
     *
     * @param contentRequestEntity
     */
    public abstract void process(RequestEntity contentRequestEntity);
}
