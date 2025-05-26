package server.core;

import common.exchange.CommandRequest;
import common.exchange.CommandResponse;
import server.commands.CommandFactory;
import server.network.CommandExecutor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Основной сервер: слушает порт, принимает клиента, обрабатывает команды.
 */
public class ServerApp {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);


    private final int port;
    private final CommandExecutor executor;

    /**
     * Конструктор сервера.
     *
     * @param port порт, на котором слушает сервер
     * @param commandFactory фабрика команд
     */
    public ServerApp(int port, CommandFactory commandFactory) {
        this.port = port;
        this.executor = new CommandExecutor(commandFactory);
    }

    /**
     * Основной цикл: приём клиента, чтение, выполнение команды, отправка ответа.
     */
    public void run() {
        logger.info("Сервер запущен на порту {}", port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Клиент подключён: {}", clientSocket.getInetAddress());

                try (
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
                ) {
                    while (true) {
                        try {
                            Object obj = in.readObject();
                            if (!(obj instanceof CommandRequest request)) break;

                            logger.info("Получена команда от клиента: {}", request.getCommandName());

                            if (request.getCommandName().equals("exit")) {
                                logger.info("Клиент завершил соединение.");
                                break;
                            }

                            CommandResponse response = executor.execute(request);
                            out.writeObject(response);
                            out.flush();

                        } catch (EOFException e) {
                            logger.info("Клиент оборвал соединение.");
                            break;
                        } catch (Exception e) {
                            logger.error("Ошибка при выполнении команды: {}", e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    logger.error("Ошибка соединения с клиентом: {}", e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка запуска сервера: {}", e.getMessage());
        }
    }

}



