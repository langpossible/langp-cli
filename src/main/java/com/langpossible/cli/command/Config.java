package com.langpossible.cli.command;

import com.langpossible.cli.command.subcommand.config.ConfigSet;
import picocli.CommandLine;

@CommandLine.Command(
        name = "config",
        description = "Configure the Lang Possible Cli",
        mixinStandardHelpOptions = true,
        subcommands = {
           ConfigSet.class
        }
)
public class Config implements Runnable {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new CommandLine(new Config()).execute(args);
    }

}
