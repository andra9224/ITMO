package server.commands;

import java.util.Map;

/**
 * Команда вывода справки по всем доступным командам.
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    /**
     * Конструктор.
     * @param commands множество всех зарегистрированных команд
     */
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Возвращает описание всех доступных команд.
     *
     * @param args не используются
     * @param data не используется
     * @return строка со списком команд и их описанием
     */
    @Override
    public Object execute(String[] args, Object data) {
        StringBuilder sb = new StringBuilder("Список доступных команд:\n");
        commands.forEach((name, cmd) ->
                sb.append(String.format("  %-25s - %s%n", name, cmd.getDescription())));
        return sb.toString();
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
