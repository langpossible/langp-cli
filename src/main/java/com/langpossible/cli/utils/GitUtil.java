package com.langpossible.cli.utils;

import cn.hutool.json.JSONUtil;
import com.langpossible.cli.config.GlobalConfig;
import com.langpossible.cli.result.Result;
import org.tinylog.Logger;

public class GitUtil {

    public static Boolean clone(String name, String url) {
        String projectPath = GlobalConfig.REPOSITORY_PATH + "/" + name;
        boolean exists = DirectoryUtil.isExists(projectPath);
        Result result;
        if (!exists) {
            Logger.info("[git clone {}]", url);
            result = CommandUtil.run(GlobalConfig.REPOSITORY_PATH, "git", "clone", url);
            Logger.info("[git clone {}] <result: >", url, JSONUtil.toJsonStr(result));
        } else {
            Logger.info("[git pull {}]", url);
            result = CommandUtil.run(projectPath, "git", "pull");
            Logger.info("[git clone {}] <result: >", url, JSONUtil.toJsonStr(result));
        }
        return result.exitCode == 0;
    }

}
