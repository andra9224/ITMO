package server.core;

import common.exchange.CommandRequest;
import common.exchange.CommandResponse;
import server.commands.CommandFactory;
import server.network.CommandExecutor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Многопоточный сервер: принимает клиентов и обрабатывает запросы в параллельных потоках.
 */
public class ServerApp {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);

    private final int port;
    private final CommandExecutor executor;
    private final ExecutorService requestPool;

    public ServerApp(int port, CommandFactory commandFactory) {
        this.port = port;
        this.executor = new CommandExecutor(commandFactory);
        this.requestPool = Executors.newFixedThreadPool(10); // Пул потоков для обработки команд
    }

    /**
     * Запускает сервер и слушает клиентов.
     */
    public void run() {
        logger.info("Сервер запущен на порту {}", port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Создаём поток на каждого клиента
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            logger.error("Ошибка при запуске сервера: {}", e.getMessage(), e);
        }
    }

    /**
     * Обрабатывает одного клиента: приём и отправка сообщений.
     */
    private void handleClient(Socket clientSocket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            logger.info("Клиент подключён: {}", clientSocket.getRemoteSocketAddress());

            while (true) {
                Object obj;
                try {
                    obj = in.readObject();
                } catch (EOFException e) {
                    logger.info("Клиент отключился: {}", clientSocket.getRemoteSocketAddress());
                    break;
                }

                if (!(obj instanceof CommandRequest request)) {
                    logger.warn("Получен некорректный объект от клиента.");
                    continue;
                }

                if (request.getCommandName().equals("exit")) {
                    logger.info("Клиент запросил завершение.");
                    break;
                }

                // Передаём обработку запроса в пул потоков
                requestPool.submit(() -> {
                    try {
                        logger.info("Получена команда от {} ({}): {} {}",
                                request.getUsername(),
                                clientSocket.getRemoteSocketAddress(),
                                request.getCommandName(),
                                String.join(" ", request.getArguments())
                        );


                        CommandResponse response = executor.execute(request);

                        // Отправка ответа — в отдельном потоке
                        new Thread(() -> {
                            try {
                                synchronized (out) { // важен для ObjectOutputStream
                                    out.writeObject(response);
                                    out.flush();
                                }
                            } catch (IOException e) {
                                logger.error("Ошибка при отправке ответа: {}", e.getMessage());
                            }
                        }).start();

                    } catch (Exception e) {
                        logger.error("Ошибка при обработке запроса: {}", e.getMessage());
                    }
                });
            }

        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка при работе с клиентом: {}", e.getMessage(), e);
        }
    }
}




