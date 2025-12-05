package com.langpossible.cli.components;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class OptionSelector {

    private final Terminal terminal;

    private final BindingReader reader;

    private final String prompt;

    private final List<String> options;

    private int selected = 0;

    public OptionSelector(String prompt, List<String> options) throws IOException {
        this.terminal = TerminalBuilder.builder().system(true).jna(true).build();
        this.terminal.enterRawMode();
        this.reader = new BindingReader(terminal.reader());
        this.prompt = prompt;
        this.options = options;
    }

    public OptionSelector(Terminal terminal, String prompt, List<String> options) {
        this.terminal = terminal;
        this.reader = new BindingReader(terminal.reader());
        this.prompt = prompt;
        this.options = options;
    }

    public String run() {
        while (true) {
            // 清屏并打印插件列表
            terminal.puts(org.jline.utils.InfoCmp.Capability.clear_screen);
            terminal.writer().println(this.prompt);
            for (int i = 0; i < options.size(); i++) {
                if (i == selected) {
                    terminal.writer().println("> " + options.get(i)); // 高亮当前选项
                } else {
                    terminal.writer().println("  " + options.get(i));
                }
            }
            terminal.writer().flush();

            // 捕获按键
            KeyMap<String> keyMap = new KeyMap<>();
            keyMap.bind("up", "\033[A");    // 上箭头
            keyMap.bind("down", "\033[B");  // 下箭头
            keyMap.bind("enter", "\r");
            String key = reader.readBinding(keyMap);

            terminal.writer().flush();
            if ("up".equals(key)) {
                selected = (selected - 1 + options.size()) % options.size();
            } else if ("down".equals(key)) {
                selected = (selected + 1) % options.size();
            } else if ("enter".equals(key)) {
                String choice = options.get(selected);
                terminal.writer().println("你选择了: " + choice);
                terminal.writer().flush();
                return choice;
            }
        }
    }

    public void close() throws IOException {
        terminal.close();
    }

}

