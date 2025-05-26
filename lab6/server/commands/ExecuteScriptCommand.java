package server.commands;

import java.util.*;

/**
 * Команда для выполнения команд из указанного файла (скрипта).
 */
public class ExecuteScriptCommand implements Command {
    private final CommandFactory commandFactory;
    private final Set<String> executingScripts = new HashSet<>();

    /**
     * Конструктор.
     * @param commandFactory фабрика команд для создания нужных экземпляров
     */
    public ExecuteScriptCommand(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    /**
     * не используется
     */
    @Override
    public Object execute(String[] args, Object data) {

        return data;
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла";
    }
}
