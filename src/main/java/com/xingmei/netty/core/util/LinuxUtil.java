package com.xingmei.netty.core.util;

import org.apache.commons.exec.*;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 执行linux命令
 *
 * @Date 2019/10/11 11:50
 * @Auther cheerUpPing@163.com
 */
public class LinuxUtil {

    /**
     * 执行命令(不返回执行结果)
     *
     * @throws Exception
     */
    public static void execCmdWithoutResult(String directory, String cmd) throws Exception {
        //开启windows telnet: net start telnet
        //注意：第一个空格之后的所有参数都为参数
        CommandLine cmdLine = CommandLine.parse(cmd);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        if (StringUtils.isNotBlank(directory)) {
            executor.setWorkingDirectory(new File(directory));
        }
        //设置60秒超时，执行超过60秒后会直接终止
        ExecuteWatchdog watchdog = new ExecuteWatchdog(5 * 1000L);
        executor.setWatchdog(watchdog);
        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        executor.execute(cmdLine, handler);
        //命令执行返回前一直阻塞
        handler.waitFor();
    }


    /**
     * 执行命令(返回执行结果的)
     *
     * @return
     */
    public static String execCmdWithResult(String directory, String cmd) throws Exception {

        //接收正常结果流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        CommandLine commandline = CommandLine.parse(cmd);
        DefaultExecutor executor = new DefaultExecutor();
        if (StringUtils.isNotBlank(directory)) {
            executor.setWorkingDirectory(new File(directory));
        }
        executor.setExitValues(null);
        //设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(5 * 1000L);
        executor.setWatchdog(watchdog);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
        executor.setStreamHandler(streamHandler);
        executor.execute(commandline);
        //不同操作系统注意编码，否则结果乱码
        String out = outputStream.toString("UTF-8");
        String error = errorStream.toString("UTF-8");
        return out;
    }

}
