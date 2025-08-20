package server.commands;

/**
 * Команда, исполняющая команды из скрипта.
 * На стороне клиента она уже реализована. Здесь заглушка.
 */
public class ExecuteScriptCommand implements Command {
    private final CommandFactory commandFactory;

    public ExecuteScriptCommand(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        return "Команда 'execute_script' выполняется на клиенте. Сервер принял данные.";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла";
    }
}