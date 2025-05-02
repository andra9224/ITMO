package commands;

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
     * Выполняет команду: выводит список всех доступных команд с описанием.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Доступные команды:");
        commands.forEach((name, cmd) ->
                System.out.printf("%-25s - %s%n", name, cmd.getDescription()));
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
