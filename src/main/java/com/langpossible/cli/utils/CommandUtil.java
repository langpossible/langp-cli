package com.langpossible.cli.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import com.langpossible.cli.result.Result;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.tinylog.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CommandUtil {

    public static Result run(String dirPath, String... commands) {
        Assert.isTrue(commands.length > 0, "命令不能为空");

        String commandsStr = CharSequenceUtil.join(" ", (Object) commands);
        Logger.info("运行命令：{}", commandsStr);
        CommandLine cmdLine = CommandLine.parse(commandsStr);
        DefaultExecutor executor = new DefaultExecutor();

        if (CharSequenceUtil.isNotBlank(dirPath)) {
            // 确保目录存在
            boolean flag = DirectoryUtil.ensureDirectory(dirPath);
            Assert.isTrue(flag, "目录 [" + dirPath + "] 不存在");
            // 设置工作目录
            executor.setWorkingDirectory(new File(dirPath));
        }

        // 捕获输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
        executor.setStreamHandler(streamHandler);

        try {
            int exitCode = executor.execute(cmdLine);
            return new Result(exitCode, outputStream.toString(), errorStream.toString());
        } catch (IOException e) {
            Logger.error("", e);
            return new Result(-1, "", e.getMessage());
        }
    }

}
