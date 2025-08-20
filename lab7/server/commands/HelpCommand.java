package server.commands;

import java.util.Map;

/**
 * Команда вывода справки по всем доступным командам.
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    /**
     * Конструктор.
     *
     * @param commands список всех зарегистрированных команд
     */
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        StringBuilder sb = new StringBuilder("Список доступных команд:\n");
        commands.forEach((name, cmd) ->
                sb.append(String.format("  %-25s - %s%n", name, cmd.getDescription())));
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
