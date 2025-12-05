package com.langpossible.command;

import com.langpossible.command.subcommand.config.ConfigSet;
import picocli.CommandLine;

@CommandLine.Command(
        name = "config",
        description = "Configure the Lang Possible Cli",
        subcommands = {
           ConfigSet.class
        }
)
public class Config implements Runnable {

    @Override
    public void run() {

    }

}
