package server.commands;

import server.db.MovieDAO;
import server.repository.MovieRepository;
import server.users.UserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика команд. Регистрирует и предоставляет команды по имени.
 */
public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final UserManager userManager;

    public CommandFactory(MovieRepository movieRepository, UserManager userManager) {
        this.userManager = userManager;

        // Просмотр (из памяти)
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(movieRepository));
        commands.put("show", new ShowCommand(movieRepository));
        commands.put("show_mine", new ShowMineCommand(movieRepository));
        commands.put("show_by_owner", new ShowByOwnerCommand(movieRepository));
        commands.put("filter_starts_with_name", new FilterByNameCommand(movieRepository));
        commands.put("min_by_usa_box_office", new MinByUsaBoxOfficeCommand(movieRepository));
        commands.put("count_less_than_length", new CountLessThanLengthCommand(movieRepository));

        // Модификация (БД → память через репозиторий)
        commands.put("add", new AddCommand(movieRepository));
        commands.put("update", new UpdateCommand(movieRepository));
        commands.put("remove_by_id", new RemoveByIdCommand(movieRepository));
        commands.put("clear", new ClearCommand(movieRepository));
        commands.put("add_if_max", new AddIfMaxCommand(movieRepository));
        commands.put("add_if_min", new AddIfMinCommand(movieRepository));
        commands.put("remove_lower", new RemoveLowerCommand(movieRepository));

        // Прочее
        commands.put("execute_script", new ExecuteScriptCommand(this));

        // Аутентификация
        commands.put("register", new RegisterCommand(userManager));
        commands.put("login", new LoginCommand(userManager));
    }

    public Command getCommand(String name) { return commands.get(name); }
    public Map<String, Command> getAllCommands() { return commands; }
    public UserManager getUserManager() { return userManager; }
}
