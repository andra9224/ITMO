package commands;

import collection.MovieCollection;
import input.MovieInputParser;
import storage.JsonFileManager;

import java.util.*;

/**
 * Фабрика команд. Хранит все доступные команды и возвращает экземпляры по имени.
 * Также умеет создавать команды с пользовательским Scanner'ом (например, для скриптов).
 */
public class CommandFactory {
    /**
     * Хранилище всех доступных команд.
     */
    private final Map<String, Command> commands = new HashMap<>();
    private final Scanner scanner;
    private final MovieCollection collection;
    private final JsonFileManager fileManager;
    private final String fileName;

    /**
     * Конструктор. Регистрирует все команды при создании.
     *
     * @param collection коллекция фильмов
     * @param fileManager менеджер файлов (чтение/запись JSON)
     * @param fileName имя файла коллекции
     * @param scanner сканер ввода
     */
    public CommandFactory(MovieCollection collection, JsonFileManager fileManager, String fileName, Scanner scanner) {
        this.collection = collection;
        this.scanner = scanner;
        this.fileManager = fileManager;
        this.fileName = fileName;

        MovieInputParser parser = new MovieInputParser(scanner);

        // Регистрация команд
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collection));
        commands.put("show", new ShowCommand(collection));
        commands.put("add", new AddCommand(collection, parser));
        commands.put("update", new UpdateCommand(collection, parser));
        commands.put("remove_by_id", new RemoveByIdCommand(collection));
        commands.put("clear", new ClearCommand(collection));
        commands.put("save", new SaveCommand(collection, fileManager, fileName));
        commands.put("exit", new ExitCommand());
        commands.put("add_if_max", new AddIfMaxCommand(collection, parser));
        commands.put("add_if_min", new AddIfMinCommand(collection, parser));
        commands.put("remove_lower", new RemoveLowerCommand(collection, parser));
        commands.put("min_by_usa_box_office", new MinByUsaBoxOfficeCommand(collection));
        commands.put("count_less_than_length", new CountLessThanLengthCommand(collection));
        commands.put("filter_starts_with_name", new FilterByNameCommand(collection));
        commands.put("execute_script", new ExecuteScriptCommand(this));
    }

    /**
     * Возвращает команду по имени.
     *
     * @param name имя команды
     * @return соответствующая команда или null, если не найдена
     */
    public Command getCommand(String name) {

        return commands.get(name);
    }

    /**
     * Возвращает коллекцию всех доступных команд.
     *
     * @return коллекция команд
     */
    public Collection<Command> getAllCommands() {

        return commands.values();
    }

    /**
     * Возвращает команду, созданную с кастомным Scanner'ом.
     * Используется при выполнении скриптов.
     *
     * @param name имя команды
     * @param customScanner сканер, из которого читаются данные (например, из файла)
     * @return команда, готовая к использованию
     */
    public Command getCommandWithCustomScanner(String name, Scanner customScanner) {
        MovieInputParser parser = new MovieInputParser(customScanner);

        return switch (name) {
            case "add" -> new AddCommand(collection, parser);
            case "add_if_max" -> new AddIfMaxCommand(collection, parser);
            case "add_if_min" -> new AddIfMinCommand(collection, parser);
            case "remove_lower" -> new RemoveLowerCommand(collection, parser);
            case "update" -> new UpdateCommand(collection, parser);
            default -> getCommand(name); // команды без ввода
        };
    }
}