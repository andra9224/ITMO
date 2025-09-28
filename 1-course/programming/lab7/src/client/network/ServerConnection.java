package client.network;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

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

    /** Таймаут ожидания ответа сервера (мс). Подбери при необходимости. */
    private static final int SO_TIMEOUT_MS = 5000;

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
        socket.setSoTimeout(SO_TIMEOUT_MS);

        out = new ObjectOutputStream(socket.getOutputStream());
        in  = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Отправляет объект серверу.
     * @param obj объект для отправки
     * @throws IOException если произошла ошибка записи
     */
    public void send(Object obj) throws IOException {
        ensureOpen();
        out.writeObject(obj);
        out.flush();
    }

    /**
     * Получает объект от сервера.
     * @return объект от сервера
     * @throws IOException если произошла ошибка чтения или таймаут
     * @throws ClassNotFoundException если класс объекта не найден
     */
    public Object receive() throws IOException, ClassNotFoundException {
        ensureOpen();
        try {
            return in.readObject();
        } catch (SocketTimeoutException e) {
            throw new IOException("Таймаут ожидания ответа от сервера (" + SO_TIMEOUT_MS + " мс).", e);
        }
    }

    /** Закрывает соединение и потоки. */
    public void close() {
        try { if (in != null) in.close(); } catch (IOException ignored) {}
        try { if (out != null) out.close(); } catch (IOException ignored) {}
        try { if (socket != null && !socket.isClosed()) socket.close(); } catch (IOException ignored) {}
    }

    private void ensureOpen() throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new IOException("Соединение с сервером закрыто.");
        }
    }
}
