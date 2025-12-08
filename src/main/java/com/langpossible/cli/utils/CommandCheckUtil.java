package com.langpossible.cli.utils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.tinylog.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CommandCheckUtil {

    public static boolean isCommandAvailable(String... commands) {
        for (String command : commands) {
            if (!isCommandAvailable(command)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCommandAvailable(String command) {
        // 根据操作系统选择命令
        String os = System.getProperty("os.name").toLowerCase();
        String checkCmd = os.contains("win") ? "where " + command : "which " + command;

        CommandLine cmdLine = CommandLine.parse(checkCmd);
        DefaultExecutor executor = new DefaultExecutor();

        // 捕获输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);

        try {
            int exitCode = executor.execute(cmdLine);
            return exitCode == 0 && !outputStream.toString().isBlank();
        } catch (ExecuteException e) {
            // 命令不存在时通常会抛出 ExecuteException
            return false;
        } catch (IOException e) {
            Logger.error("", e);
            return false;
        }
    }

}
