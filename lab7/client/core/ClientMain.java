package client.core;

import client.network.ServerConnection;
import common.exchange.CommandRequest;
import common.exchange.CommandResponse;

import java.util.Scanner;

/**
 * Точка входа клиентского приложения.
 * Устанавливает соединение с сервером и запускает основной цикл.
 *
 *
 *
 *
 *
 * добавить различные команды вывода фильмов(все, свои, по никнейму) добавить при выводе фильмов вывод никнейма
 * проверить все условия со скриншота и просмотреть и понять все, проверить убрать лишние коменты
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class ClientMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        if (args.length >= 1) host = args[0];
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат порта.");
            }
        }

        ServerConnection connection = new ServerConnection(host, port);
        try {
            connection.connect();
            Scanner scanner = new Scanner(System.in);

            String username = null;
            String passwordHash = null;

            while (true) {
                System.out.println("Добро пожаловать!");
                System.out.println("1. Войти");
                System.out.println("2. Зарегистрироваться");
                System.out.println("3. Выход");
                System.out.print("> ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1" -> { // вход
                        System.out.print("Введите логин: ");
                        username = scanner.nextLine().trim();
                        System.out.print("Введите пароль: ");
                        String password = scanner.nextLine().trim();
                        passwordHash = md5(password);

                        CommandRequest loginRequest = new CommandRequest("login",
                                new String[]{username, passwordHash}, null, username, passwordHash);
                        connection.send(loginRequest);
                        CommandResponse loginResponse = (CommandResponse) connection.receive();

                        System.out.println(loginResponse.getMessage());

                        if (loginResponse.isSuccess()) {
                            new ClientApp(connection, username, passwordHash).run(); // ← запуск ТОЛЬКО при успехе
                            return;
                        } else {
                            System.out.println("Вход не выполнен. Проверьте логин и пароль.\n");
                        }
                    }
                    case "2" -> { // регистрация
                        System.out.print("Введите логин: ");
                        username = scanner.nextLine().trim();
                        System.out.print("Введите пароль: ");
                        String password = scanner.nextLine().trim();
                        passwordHash = md5(password);

                        CommandRequest registerRequest = new CommandRequest("register",
                                new String[]{username, passwordHash}, null, username, passwordHash);
                        connection.send(registerRequest);
                        CommandResponse registerResponse = (CommandResponse) connection.receive();

                        System.out.println(registerResponse.getMessage());

                        if (registerResponse.isSuccess()) {
                            new ClientApp(connection, username, passwordHash).run(); // ← запуск ТОЛЬКО при успехе
                            return;
                        } else {
                            System.out.println("Регистрация не удалась. Возможно, логин уже занят.\n");
                        }
                    }
                    case "3" -> {
                        System.out.println("До свидания.");
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Введите 1, 2 или 3.");
                }
            }

        } catch (Exception e) {
            System.out.println("Не удалось подключиться к серверу: " + e.getMessage());
        }
    }

    private static String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка хеширования MD5", e);
        }
    }
}

