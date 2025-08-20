package server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.commands.CommandFactory;
import server.db.MovieDAO;
import server.users.UserManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

/**
 * Точка входа для запуска серверного приложения.
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        int port = 5000;

        try {
            logger.info("Запуск сервера...");

            // Подключение к PostgreSQL
            String dbUrl = "jdbc:postgresql://localhost:9224/lab_movies";
            String dbUser = "postgres";
            String dbPassword = "basket21";
            Connection dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Соединение с базой данных установлено.");

            // DAO и менеджеры
            MovieDAO movieDAO = new MovieDAO(dbConnection);
            UserManager userManager = new UserManager(dbConnection);
            CommandFactory commandFactory = new CommandFactory(movieDAO, userManager);

            // Запуск сервера
            ServerApp server = new ServerApp(port, commandFactory);
            new Thread(server::run).start();
            logger.info("Сервер запущен на порту {}", port);

            // Консоль администратора
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine().trim();
                if (input.equals("exit")) {
                    logger.info("Завершение работы сервера по команде администратора.");
                    System.exit(0);
                } else {
                    logger.warn("Неизвестная команда в консоли сервера: '{}'", input);
                }
            }

        } catch (Exception e) {
            logger.error("Ошибка инициализации сервера: {}", e.getMessage(), e);
        }
    }
}
