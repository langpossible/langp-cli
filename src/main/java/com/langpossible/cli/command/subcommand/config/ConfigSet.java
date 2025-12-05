package com.langpossible.cli.command.subcommand.config;

import picocli.CommandLine;

@CommandLine.Command(
        name = "set",
        description = "Set a config"
)
public class ConfigSet implements Runnable {

    @CommandLine.Option(names = {"-g"}, description = "启用全局配置模式")
    private boolean global;

    @CommandLine.Parameters(paramLabel = "<arg> <value>")
    private String[] args;

    @Override
    public void run() {
    }

}
