package server.commands;

import server.db.MovieDAO;
import server.users.UserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика команд. Регистрирует и предоставляет команды по имени.
 */
public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final UserManager userManager;

    /**
     * Создаёт фабрику команд.
     *
     * @param movieDAO    DAO для фильмов
     * @param userManager менеджер пользователей
     */
    public CommandFactory(MovieDAO movieDAO, UserManager userManager) {
        this.userManager = userManager;

        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(movieDAO));
        commands.put("show", new ShowCommand(movieDAO));
        commands.put("add", new AddCommand(movieDAO));
        commands.put("update", new UpdateCommand(movieDAO));
        commands.put("remove_by_id", new RemoveByIdCommand(movieDAO));
        commands.put("clear", new ClearCommand(movieDAO));
        commands.put("add_if_max", new AddIfMaxCommand(movieDAO));
        commands.put("add_if_min", new AddIfMinCommand(movieDAO));
        commands.put("remove_lower", new RemoveLowerCommand(movieDAO));
        commands.put("min_by_usa_box_office", new MinByUsaBoxOfficeCommand(movieDAO));
        commands.put("count_less_than_length", new CountLessThanLengthCommand(movieDAO));
        commands.put("filter_starts_with_name", new FilterByNameCommand(movieDAO));
        commands.put("execute_script", new ExecuteScriptCommand(this));

        commands.put("register", new RegisterCommand(userManager));
        commands.put("login", new LoginCommand(userManager));
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    public Map<String, Command> getAllCommands() {
        return commands;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}