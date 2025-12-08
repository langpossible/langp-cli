package com.langpossible.cli.utils;

import java.io.File;

public class DirectoryUtil {

    /**
     * 判断目录是否存在，不存在则创建
     * @param path 目录路径 {@link String}
     * @return {@link Boolean}
     */
    public static boolean ensureDirectory(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            return dir.isDirectory();
        } else {
            return dir.mkdirs();
        }
    }

    /**
     * 判断目录是否存在
     * @param path 目录路径 {@link String}
     * @return {@link Boolean}
     */
    public static boolean isExists(String path) {
        File dir = new File(path);
        return dir.exists();
    }

}
