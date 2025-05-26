package client.core;

import client.input.MovieInputParser;
import client.network.ServerConnection;
import common.exchange.CommandRequest;
import common.exchange.CommandResponse;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

/**
 * Основной цикл клиентского приложения.
 * Считывает команды от пользователя, отправляет их серверу и выводит ответы.
 */
public class ClientApp {
    private final Scanner scanner;
    private final ServerConnection connection;
    private final MovieInputParser movieParser;

    public ClientApp(ServerConnection connection) {
        this.scanner = new Scanner(System.in);
        this.connection = connection;
        this.movieParser = new MovieInputParser(scanner);
    }

    /**
     * Запускает цикл клиентского интерфейса.
     */
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
                String[] args = (parts.length > 1) ? new String[]{parts[1]} : new String[0];

                if (commandName.equals("exit")) {
                    try {
                        CommandRequest exitRequest = new CommandRequest("exit", new String[0], null);
                        connection.send(exitRequest);  // отправим серверу
                    } catch (IOException e) {
                        System.out.println("Не удалось уведомить сервер об отключении.");
                    }
                    System.out.println("Клиент завершает работу.");
                    break;
                }

                Object data = null;

                if (commandName.equals("add")
                        || commandName.equals("add_if_max")
                        || commandName.equals("add_if_min")
                        || commandName.equals("remove_lower")
                        || commandName.equals("update")) {
                    System.out.println("Введите данные фильма:");
                    data = movieParser.parseMovie();
                }

                if (commandName.equals("execute_script")) {
                    if (args.length == 0) {
                        System.out.println("Укажите имя файла для execute_script.");
                    } else {
                        executeScript(args[0]);
                    }
                    continue;
                }

                CommandRequest request = new CommandRequest(commandName, args, data);
                connection.send(request);

                CommandResponse response = (CommandResponse) connection.receive();
                System.out.println(response.getMessage());

                Object data1 = response.getData();
                if (data1 instanceof Collection<?> collection) {
                    for (Object item : collection) {
                        System.out.println(item);
                    }
                } else if (data1 != null) {
                    System.out.println(data1);
                }


            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        connection.close();
    }

    public void executeScript(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не найден: " + fileName);
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                System.out.println("> " + line);

                String[] parts = line.split(" ", 2);
                String commandName = parts[0];
                String[] args = (parts.length > 1) ? new String[]{parts[1]} : new String[0];

                Object data = null;
                if (commandName.equals("add")
                        || commandName.equals("add_if_max")
                        || commandName.equals("add_if_min")
                        || commandName.equals("remove_lower")
                        || commandName.equals("update")) {
                    System.out.println("→ Чтение Movie из скрипта");
                    MovieInputParser parser = new MovieInputParser(fileScanner);
                    data = parser.parseMovie();
                }

                CommandRequest request = new CommandRequest(commandName, args, data);
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
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении скрипта: " + e.getMessage());
        }
    }


}
