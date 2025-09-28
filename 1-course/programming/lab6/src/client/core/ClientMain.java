package client.core;

import client.network.ServerConnection;

/**
 * Точка входа клиентского приложения.
 * Устанавливает соединение с сервером и запускает основной цикл.
 */
public class ClientMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        if (args.length >= 1) {
            host = args[0];
        }
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
            new ClientApp(connection).run();
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к серверу: " + e.getMessage());
        }
    }
}
