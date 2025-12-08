package com.langpossible.cli.utils;

import java.io.File;

public class MavenDependencyCheckerUtil {

    /**
     * 检查本地 Maven 仓库中是否存在某个依赖
     * @param groupId    依赖的 groupId {@link String}
     * @param artifactId 依赖的 artifactId {@link String}
     * @param version    依赖的版本 {@link String}
     * @return {@link Boolean}
     */
    public static boolean isDependencyInstalled(String groupId, String artifactId, String version) {
        // 获取本地仓库路径，默认 ~/.m2/repository
        String repoPath = System.getProperty("user.home") + "/.m2/repository";

        // groupId 转换成路径
        String groupPath = groupId.replace('.', '/');

        // 构造依赖 jar 路径
        String jarPath = String.format("%s/%s/%s/%s/%s-%s.jar",
                repoPath, groupPath, artifactId, version, artifactId, version);

        File jarFile = new File(jarPath);
        return jarFile.exists();
    }

}
