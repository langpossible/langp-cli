package com.langpossible.cli.command.subcommand.plugin;

import cn.hutool.core.lang.Assert;
import com.langpossible.cli.components.OptionSelector;
import com.langpossible.cli.config.GlobalConfig;
import com.langpossible.cli.result.Result;
import com.langpossible.cli.utils.CommandUtil;
import com.langpossible.cli.utils.DirectoryUtil;
import com.langpossible.cli.utils.GitUtil;
import com.langpossible.cli.utils.MavenDependencyUtil;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.tinylog.Logger;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "init",
        description = "Initialize a new plugin"
)
public class PluginInit implements Runnable {

    @Override
    public void run() {
        try {
            Terminal terminal = TerminalBuilder.builder().system(true).jna(true).build();
            terminal.enterRawMode();
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

            terminal.writer().println("编辑插件的资料：");

            /* Step 1: 资料输入 */
            // Step 1-1: 插件名称
            String name = reader.readLine("插件名称（按下回车键进入下一步）： ");
            // Step 1-2: 插件描述
            String description = reader.readLine("插件描述（按下回车键进入下一步）： ");
            // Step 1-3: 插件作者
            String author = reader.readLine("插件作者（按下回车键进入下一步）： ");
            // Step 1-4: 插件版本
            String version = reader.readLine("插件版本（按下回车键进入下一步）： ");
            // Step 1-5: 插件类型
            List<String> options = List.of("tool", "model");
            OptionSelector selector = new OptionSelector(terminal, "请选择插件类型：", options);
            String type = selector.run();

            /* Step 2: 插件初始化 */
            // Step 2-1: 检查核心组件
            terminal.writer().println("正在检查核心组件...");
            terminal.flush();
            Boolean langpCoreCloneFlag = GitUtil.clone("langp-core", "https://github.com/langpossible/langp-core.git");
            Assert.isTrue(langpCoreCloneFlag, "插件初始化失败，核心组件下载异常");
            // Step 2-2: 检查核心组件依赖
            terminal.writer().println("正在检查核心组件依赖...");
            terminal.flush();
            boolean langpCoreInstallFlag = MavenDependencyUtil.install("com.langpossible", "langp-core", "1.0.1", GlobalConfig.REPOSITORY_PATH + "/" + "langp-core");
            Assert.isTrue(langpCoreInstallFlag, "插件初始化失败，核心组件安装异常");
            // Step 2-3: 安装插件原型
            terminal.writer().println("正在安装插件原型...");
            terminal.flush();
            Boolean archetypeCloneFlag = GitUtil.clone("langp-plugin-archetype", "https://github.com/langpossible/langp-plugin-archetype.git");
            Assert.isTrue(archetypeCloneFlag, "插件初始化失败，插件原型下载异常");
            // Step 2-4: 编译插件原型
            terminal.writer().println("正在编译插件原型...");
            terminal.flush();
            boolean archetypeInstallFlag = MavenDependencyUtil.install("com.langpossible", "langp-plugin-archetype", "1.0.1", GlobalConfig.REPOSITORY_PATH + "/" + "langp-archetype-plugin");
            Assert.isTrue(archetypeInstallFlag, "插件初始化失败，插件原型安装异常");
            // Step 2-5: 创建插件项目
            terminal.writer().println("正在创建插件项目...");
            terminal.flush();
            Result createProjectResult = CommandUtil.run(
                    System.getProperty("user.dir"),
                    "mvn",
                    "archetype:generate",
                    "-DarchetypeGroupId=com.langpossible",
                    "-DarchetypeArtifactId=langp-plugin-archetype",
                    "-DarchetypeVersion=1.0.1",
                    "-DgroupId=com.langpossible.plugin",
                    "-DartifactId=" + name.toLowerCase() + "-plugin",
                    "-DName=" + name,
                    "-DDescription=\"" + description + "\"",
                    "-DAuthor=" + author,
                    "-DVersion=" + version,
                    "-DType=" + type,
                    "-B");

            Assert.isTrue(createProjectResult.exitCode == 0, "插件初始化失败，插件项目创建异常");

            terminal.writer().println("插件初始化成功，请进入项目目录进行下一步操作：");
            terminal.flush();
            terminal.close();
        } catch (Exception e) {
            Logger.error("插件初始化异常，异常原因：{}", e.getMessage());
        }
    }

}
