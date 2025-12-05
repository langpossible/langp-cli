package com.langpossible.cli.command.subcommand.plugin;

import com.langpossible.cli.components.OptionSelector;
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

            // Step 1: 插件名称
            String pluginName = reader.readLine("插件名称（按下回车键进入下一步）： ");

            // Step 2: 插件描述
            String pluginDescription = reader.readLine("插件描述（按下回车键进入下一步）： ");

            // Step 3: 插件作者
            String pluginAuthor = reader.readLine("插件作者（按下回车键进入下一步）： ");

            // Step 4: 插件版本
            String pluginVersion = reader.readLine("插件版本（按下回车键进入下一步）： ");

            // Step 6: 插件类型
            List<String> options = List.of("工具", "模型");
            OptionSelector selector = new OptionSelector(terminal, "请选择插件类型：", options);
            String pluginType = selector.run();

        } catch (Exception e) {
            Logger.error("", e);
        }
    }

}
