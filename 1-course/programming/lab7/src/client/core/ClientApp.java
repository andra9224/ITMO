package client.core;

import client.input.MovieInputParser;
import client.network.ServerConnection;
import common.exchange.CommandRequest;
import common.exchange.CommandResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Основной цикл клиентского приложения.
 * Считывает команды от пользователя, отправляет их серверу и выводит ответы.
 */
public class ClientApp {
    private final Scanner scanner;
    private final ServerConnection connection;
    private final MovieInputParser movieParser;
    private final String username;
    private final String passwordHash;

    /** Стек исполняемых скриптов (для сообщений об ошибках и глубины). */
    private final Deque<Path> scriptStack = new ArrayDeque<>();
    /** Множество активных скриптов (по каноническому пути) — защита от рекурсии/циклов. */
    private final Set<Path> activeScripts = new HashSet<>();
    /** Максимальная допустимая глубина вложенности execute_script. */
    private static final int MAX_SCRIPT_DEPTH = 20;

    public ClientApp(ServerConnection connection, String username, String passwordHash) {
        this.scanner = new Scanner(System.in);
        this.connection = connection;
        this.movieParser = new MovieInputParser(scanner);
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /** Запускает интерактивный режим клиента. */
    public void run() {
        System.out.println("Клиент запущен. Введите 'help' для списка команд.");

        while (true) {
            try {
                System.out.print("> ");
                if (!scanner.hasNextLine()) break;

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split(" ", 2);
                String commandName = parts[0];
                String[] args = (parts.length > 1)
                        ? splitArgs(parts[1])
                        : new String[0];

                if (commandName.equals("exit")) {
                    try {
                        CommandRequest exitRequest = new CommandRequest("exit", new String[0], null, username, passwordHash);
                        connection.send(exitRequest);
                    } catch (IOException ignored) {}
                    System.out.println("Клиент завершает работу.");
                    break;
                }

                Object data = null;
                if (commandRequiresMovie(commandName)) {
                    System.out.println("Введите данные фильма:");
                    data = movieParser.parseMovie();
                }

                if (commandName.equals("execute_script")) {
                    if (args.length == 0) {
                        System.out.println("Укажите имя файла.");
                    } else {
                        executeScript(args[0]);
                    }
                    continue;
                }

                CommandRequest request = new CommandRequest(commandName, args, data, username, passwordHash);
                connection.send(request);

                CommandResponse response = (CommandResponse) connection.receive();
                System.out.println(response.getMessage());

                Object responseData = response.getData();
                if (responseData instanceof Collection<?> collection) {
                    for (Object item : collection) {
                        System.out.println(item);
                    }
                } else if (responseData != null) {
                    System.out.println(responseData);
                }

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        connection.close();
    }

    /**
     * Выполняет команды из скрипта (поддерживает вложенность и защиту от рекурсии).
     *
     * @param fileName путь к файлу
     */
    public void executeScript(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists() || !file.isFile()) {
                System.out.println("Файл не найден: " + fileName);
                return;
            }

            Path canonical = file.getCanonicalFile().toPath();

            if (scriptStack.size() >= MAX_SCRIPT_DEPTH) {
                System.out.println("Превышена максимальная глубина вложенности скриптов (" + MAX_SCRIPT_DEPTH + "). Остановлено.");
                return;
            }
            if (activeScripts.contains(canonical)) {

                String cycle = formatCycle(canonical);
                System.out.println("Обнаружен рекурсивный вызов скрипта. Цикл: " + cycle);
                return;
            }

            scriptStack.push(canonical);
            activeScripts.add(canonical);
            System.out.println("→ Выполнение скрипта: " + canonical);

            try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    System.out.println("> " + line);

                    String[] parts = line.split(" ", 2);
                    String commandName = parts[0];
                    String[] args = (parts.length > 1)
                            ? splitArgs(parts[1])
                            : new String[0];

                    if ("execute_script".equals(commandName)) {
                        if (args.length == 0) {
                            System.out.println("Укажите имя файла для execute_script.");
                        } else {
                            executeScript(args[0]); // рекурсивный вызов с защитой
                        }
                        continue;
                    }

                    Object data = null;
                    if (commandRequiresMovie(commandName)) {
                        System.out.println("→ Чтение Movie из скрипта");
                        MovieInputParser parser = new MovieInputParser(fileScanner);
                        data = parser.parseMovie();
                    }

                    CommandRequest request = new CommandRequest(commandName, args, data, username, passwordHash);
                    connection.send(request);

                    CommandResponse response = (CommandResponse) connection.receive();
                    System.out.println(response.getMessage());

                    Object responseData = response.getData();
                    if (responseData instanceof Collection<?> collection) {
                        for (Object item : collection) {
                            System.out.println(item);
                        }
                    } else if (responseData != null) {
                        System.out.println(responseData);
                    }
                }
            }
        } catch (IOException io) {
            System.out.println("Ошибка при выполнении скрипта: " + io.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении скрипта: " + e.getMessage());
        } finally {
            if (!scriptStack.isEmpty()) {
                Path p = scriptStack.pop();
                activeScripts.remove(p);
                System.out.println("← Завершено: " + p);
            }
        }
    }

    /** Проверяет, требует ли команда объект Movie. */
    private boolean commandRequiresMovie(String name) {
        return name.equals("add")
                || name.equals("add_if_max")
                || name.equals("add_if_min")
                || name.equals("remove_lower")
                || name.equals("update");
    }

    /** Разбивает аргументы с учётом нескольких пробелов. */
    private String[] splitArgs(String tail) {

        return Arrays.stream(tail.trim().split("\\s+"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    /** Красиво выводит цикл рекурсивных скриптов. */
    private String formatCycle(Path candidate) {

        List<Path> chain = new ArrayList<>(scriptStack);

        Collections.reverse(chain);
        chain.add(candidate);
        return chain.stream().map(Path::toString).collect(Collectors.joining(" → "));
    }
}
