package server.core;

import server.collection.MovieCollection;
import server.commands.CommandFactory;
import server.storage.JsonFileManager;

import java.io.IOException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Точка входа для запуска серверного приложения.
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        int port = 5000;
        String fileName = "movies.json";

        logger.info("Инициализация сервера с файлом {}", fileName);


        MovieCollection collection = new MovieCollection();
        JsonFileManager fileManager = new JsonFileManager();
        CommandFactory commandFactory = new CommandFactory(collection, fileManager, fileName);

        ServerApp server = new ServerApp(port, commandFactory);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                fileManager.saveToFile(fileName, collection.getMovies());
                logger.info("Сохраняем коллекцию при завершении...");

            } catch (IOException e) {
                logger.error("Ошибка при сохранении: {}", e.getMessage());
            }
        }));

        new Thread(server::run).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("server_save")) {
                try {
                    Object result = commandFactory.getServerSaveCommand().execute(new String[0], null);
                    logger.info("Ручное сохранение выполнено: {}", result);
                } catch (Exception e) {
                    logger.error("Ошибка при выполнении server_save: {}", e.getMessage());
                }
            } else if (input.equals("exit")) {
                logger.info("Сервер завершает работу по команде администратора.");
                System.exit(0);
            } else {
                logger.warn("Неизвестная команда в консоли сервера: '{}'", input);
            }
        }
    }
}


