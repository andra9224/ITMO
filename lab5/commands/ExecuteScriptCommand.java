package commands;

import java.io.File;
import java.io.FileNotFoundException;
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
     * Выполняет команды, считанные из указанного файла.
     * @param args имя файла скрипта
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Не указано имя файла скрипта");
            return;
        }

        String scriptName = args[0];

        if (executingScripts.contains(scriptName)) {
            System.out.println("Скрипт '" + scriptName + "' уже выполняется. Рекурсивный вызов запрещён.");
            return;
        }

        File scriptFile = new File(scriptName);
        if (!scriptFile.exists() || !scriptFile.isFile()) {
            System.out.println("Файл скрипта не найден: " + scriptName);
            return;
        }

        executingScripts.add(scriptName);

        try (Scanner fileScanner = new Scanner(scriptFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                System.out.println("Выполнение команды: " + line);
                String[] parts = line.split(" ", 2);
                String commandName = parts[0];
                String[] commandArgs = parts.length > 1 ? new String[]{parts[1]} : new String[]{};

                Command command = commandFactory.getCommandWithCustomScanner(commandName, fileScanner);
                if (command != null) {
                    try {
                        command.execute(commandArgs);
                    } catch (Exception e) {
                        System.out.println("Ошибка при выполнении команды '" + commandName + "': " + e.getMessage());
                    }
                } else {
                    System.out.println("Неизвестная команда: " + commandName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден: " + scriptName);
        } finally {
            executingScripts.remove(scriptName);
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла";
    }
}
