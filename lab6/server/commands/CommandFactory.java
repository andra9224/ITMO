package server.commands;

import server.collection.MovieCollection;
import server.storage.JsonFileManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика команд. Создаёт и регистрирует команды по имени.
 */
public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final ServerSaveCommand serverSaveCommand;

    /**
     * Создаёт фабрику и регистрирует команды.
     *
     * @param collection коллекция фильмов
     * @param fileManager менеджер сохранения (для server_save)
     * @param fileName имя файла сохранения
     */
    public CommandFactory(MovieCollection collection, JsonFileManager fileManager, String fileName) {
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collection));
        commands.put("show", new ShowCommand(collection));
        commands.put("add", new AddCommand(collection));
        commands.put("update", new UpdateCommand(collection));
        commands.put("remove_by_id", new RemoveByIdCommand(collection));
        commands.put("clear", new ClearCommand(collection));
        commands.put("add_if_max", new AddIfMaxCommand(collection));
        commands.put("add_if_min", new AddIfMinCommand(collection));
        commands.put("remove_lower", new RemoveLowerCommand(collection));
        commands.put("min_by_usa_box_office", new MinByUsaBoxOfficeCommand(collection));
        commands.put("count_less_than_length", new CountLessThanLengthCommand(collection));
        commands.put("filter_starts_with_name", new FilterByNameCommand(collection));
        commands.put("execute_script", new ExecuteScriptCommand(this));

        this.serverSaveCommand = new ServerSaveCommand(collection, fileManager, fileName);


    }

    /**
     * Возвращает команду по её имени.
     * @param name имя команды
     * @return объект команды или null
     */
    public Command getCommand(String name) {
        return commands.get(name);
    }

    public Command getServerSaveCommand() {
        return serverSaveCommand;
    }


    /**
     * Возвращает все зарегистрированные команды.
     * Используется в help.
     */
    public Map<String, Command> getAllCommands() {
        return commands;
    }
}
