package com.langpossible.command.subcommand.config;

import picocli.CommandLine;

@CommandLine.Command(
        name = "set",
        description = "Set a config"
)
public class ConfigSet implements Runnable {

    @CommandLine.Option(names = {"-g"}, description = "启用全局配置模式")
    private boolean global;

    @Override
    public void run() {

    }

}
