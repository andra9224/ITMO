package client.network;

import java.io.*;
import java.net.Socket;

/**
 * Устанавливает и управляет соединением с сервером по TCP.
 * Предоставляет потоки ввода и вывода для обмена сериализованными объектами.
 */
public class ServerConnection {
    private final String host;
    private final int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Создаёт объект подключения к серверу.
     * @param host адрес сервера
     * @param port порт сервера
     */
    public ServerConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Открывает соединение и создаёт потоки ввода/вывода.
     * @throws IOException если не удалось подключиться
     */
    public void connect() throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Отправляет объект серверу.
     * @param obj объект для отправки
     * @throws IOException если произошла ошибка записи
     */
    public void send(Object obj) throws IOException {
        out.writeObject(obj);
        out.flush();
    }

    /**
     * Получает объект от сервера.
     * @return объект от сервера
     * @throws IOException если произошла ошибка чтения
     * @throws ClassNotFoundException если класс объекта не найден
     */
    public Object receive() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    /**
     * Закрывает соединение и потоки.
     */
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }
}
