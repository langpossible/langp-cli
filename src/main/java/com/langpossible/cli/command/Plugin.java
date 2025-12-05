package com.langpossible.cli.command;

import com.langpossible.cli.command.subcommand.plugin.PluginInit;
import picocli.CommandLine;

@CommandLine.Command(
        name = "plugin",
        description = "Lang Possible Plugin",
        mixinStandardHelpOptions = true,
        subcommands = {
            PluginInit.class
        }
)
public class Plugin implements Runnable {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new CommandLine(new Plugin()).execute(args);
    }

}
