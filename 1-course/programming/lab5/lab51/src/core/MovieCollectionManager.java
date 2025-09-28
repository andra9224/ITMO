package core;

import collection.MovieCollection;
import commands.Command;
import commands.CommandFactory;
import storage.JsonFileManager;
import java.util.Scanner;

/**
 * Основной управляющий класс приложения.
 * Отвечает за загрузку коллекции фильмов, инициализацию команд,
 * и запуск командного цикла для взаимодействия с пользователем.
 */
public class MovieCollectionManager {
    private final MovieCollection collection;
    private final CommandFactory commandFactory;
    private final Scanner scanner;
    private final JsonFileManager fileManager;

    /**
     * Создаёт экземпляр менеджера и инициализирует компоненты приложения.
     *
     * @param fileName имя JSON-файла, содержащего коллекцию
     */
    public MovieCollectionManager(String fileName) {
        this.collection = new MovieCollection();
        this.scanner = new Scanner(System.in);
        this.fileManager = new JsonFileManager();
        this.commandFactory = new CommandFactory(collection, fileManager, fileName, scanner);

    }

    /**
     * Запускает главный цикл командной строки для управления коллекцией.
     * Обрабатывает пользовательский ввод и выполняет соответствующие команды.
     * Завершение возможно через Ctrl+D или команду exit.
     */
    public void run() {
        System.out.println("Программа управления коллекцией фильмов");
        System.out.println("Введите 'help' для списка команд");

        while (true) {
            try {
                System.out.print("> ");

                if (!scanner.hasNextLine()) {
                    System.out.println("\nВвод завершён. Завершение программы.");
                    break;
                }

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split(" ", 2);
                Command cmd = commandFactory.getCommand(parts[0]);

                if (cmd != null) {
                    cmd.execute(parts.length > 1 ? new String[]{parts[1]} : new String[]{});
                } else {
                    System.out.println("Неизвестная команда. Введите 'help' для справки.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
