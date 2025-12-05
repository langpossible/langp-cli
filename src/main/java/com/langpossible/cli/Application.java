package com.langpossible.cli;

import com.langpossible.cli.command.Config;
import com.langpossible.cli.command.Plugin;
import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(
        name = "langp-cli",
        mixinStandardHelpOptions = true,
        subcommands = {
            Config.class,
            Plugin.class
        }
)
public class Application implements Runnable {

    @Override
    public void run() {

    }

    public static void main(String[] args) throws IOException {
        new CommandLine(new Application()).execute(args);
    }

}
